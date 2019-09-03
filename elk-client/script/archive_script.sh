#!/bin/bash
# Copyright 2019 AT&T Intellectual Property, Inc. All rights reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# See the License for the specific language governing permissions and
# limitations under the License.
#
# What this is: Script to scan Acumos model artifacts and documents as dumped
# from an Acumos platform by dump_model.sh
#
# Usage:
# $ bash archive_script.sh <actionname> <reponame>
#   actionname: action performed via archive_script.sh
#   reponame: Repository name on which action is performed.
#

function init_log() {
  set +x
  fname=$(caller 0 | awk '{print $2}')
  fline=$(caller 0 | awk '{print $1}')
  if [[ ! -d ../logs/platform-oam/elk-client ]]; then
    mkdir -p ../logs/platform-oam/elk-client
	echo; echo "$(date +%Y-%m-%d:%H:%M:%SZ), archive_script.sh($fname:$fline), $1" >>../logs/platform-oam/elk-client/archive-es.log
  fi
  set -x
}

function log() {
set +x
  init_log
  fname=$(caller 0 | awk '{print $2}')
  fline=$(caller 0 | awk '{print $1}')
  if [[ "$1" == "ERROR" ]]; then logit $fname $fline $1 "$2"
  elif [[ "$1" == "INFO" ]]; then logit $fname $fline $1 "$2"
  elif [[ "$1" == "DEBUG" ]]; then logit $fname $fline $1 "$2"
  fi
  set -x
}

function logit() {
  cat <<EOF >>../logs/platform-oam/elk-client/archive-es.log
$(date +%Y-%m-%d:%H:%M:%SZ), $3, archive_script.sh($1:$2), $4
EOF
}

function archive_esdata() {
 log INFO  "Inside archive_esdata:"
     if [ -e "$directory_name" ]
    then
        if [ -e "../../elasticsearch/data/backup/$repo" ]
        then 
            if [ -e "$directory_name/$repo" ]
            then
               log DEBUG "Archiving is already done for $repo, deleting the $repo from $directory_name and recreating"
               rm -rf $directory_name/$repo
            fi
            log DEBUG "Archiving Eleasticserach data to $directory_name"
            log DEBUG "Archiving started: $repo"
            mv -f ../../elasticsearch/data/backup/$repo/ $directory_name/
            log DEBUG "Archive Done:$repo"
            echo "$(date +%Y-%m-%d:%H:%M:%SZ),$repo"
        else            
            log ERROR "$repo:Repository not present"
            echo "$repo:Repository not present | Back up is done"
        fi    
    else
        log ERROR "$PWD/$directory_name: Directory not present"
        mkdir -p ../../elasticsearch/data/$directory_name
        log INFO "Created archive location directory: $directory_name"
        if [ -e "../../elasticsearch/data/backup/$repo" ]
        then 
            mv ../../elasticsearch/data/backup/$repo/ $directory_name/
            log DEBUG "Archive Done:$repo"
            echo "$(date +%Y-%m-%d:%H:%M:%SZ),$repo"
        else            
            log ERROR "$repo:Repository not present"
            echo "$repo:Repository not present | Back up is done"
        fi    
    fi
}

function restore_esdata() {
log INFO "Inside restore_esdata:"   
    if [ -e "$directory_name" ]
    then
        log INFO "Restore started:$repo"
        if [ -e "$directory_name/$repo" ]
        then 
            cp -r --preserve=all $directory_name/$repo/ ../../elasticsearch/data/backup/$repo/
            chown '1000:1000' ../../elasticsearch/data/backup/$repo
            log INFO "Restore done:$(date +%Y-%m-%d:%H:%M:%SZ),$repo"
            echo "$(date +%Y-%m-%d:%H:%M:%SZ),$repo"
        else
            log ERROR "$repo:Repository not found | No data available to restore"
            echo "No data present to restore"
        fi
    else
        log ERROR "$directory_name: Repository not found | No data available to restore"
        echo "No data present to restore"
    fi
}

function archive_info() {
log INFO "Inside archive_info:" 
 if [ -e "$directory_name" ]
    then
        fileExist=$(ls -l $directory_name | head -1 | awk '{print $2}')
        if [ $fileExist -gt 0 ]
        then
            #Below command is used to get list of archival with archive date in csv format 
            ls -l $directory_name --time-style="+%Y-%m-%d:%H:%M:%SZ" | awk '{print $6, $7}' | sed 's/[ \t]/,/g' | sed 's/[ /]/,/g' | awk '{if(NR>1)print}'
        else
            log ERROR "$directory_name: No data present in archive location"
			echo "No data present in archive location"
        fi    
    else
	   log ERROR "$directory_name: No data present in archive location"
       echo "No data present in archive location"
    fi
}

function archive_delete() {
log INFO "Inside archive_delete:" 
 if [ -e "$directory_name" ]
    then
        fileExist=$(ls -l $directory_name | head -1 | awk '{print $2}')
        if [ $fileExist -gt 0 ]
        then
            rm -rf $directory_name/$repo
            log DEBUG "Deleted:$repo"
            echo "$(date +%Y-%m-%d:%H:%M:%SZ),$repo"
        else
            log ERROR "$directory_name: No data present in archive location"
			echo "No data present in archive location"
        fi    
    else
       log ERROR "$directory_name: No data present in archive location"
	   echo "No data present in archive location"
    fi
}

directory_name=../../elasticsearch/data/backup/archive-es-data
WORK_DIR=$(pwd)
action=${1,,}
repo=$2
log INFO "Action Performed: $action"
if [ $action == 'archive' ]
then
  archive_esdata
elif [ $action == 'restore' ]
then
  restore_esdata 
elif [ $action == 'info' ]
then
  archive_info  
elif [ $action == 'delete' ]
then
  archive_delete 
else
    log INFO "wrong action passed"
fi
