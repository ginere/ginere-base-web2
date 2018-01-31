#!/bin/bash
SCRIPT_PATH="$(dirname "$0")";

TRUE=0
FALSE=1

function test(){
	local value="$1";
	local message="$2";

	if [ "$value" != "$TRUE" ] ; then
		echo ERROR: $message code:$value;
		exit  $FALSE;
	fi
}

function clean(){	
	find "$SCRIPT_PATH" -name \*~ -print -type f -exec rm {} \; ; 
	find "$SCRIPT_PATH" -name \*~ -print -type f -exec rm {} \; ; 
	find "$SCRIPT_PATH" -name .DS_Store -print -type f -exec rm {} \; ; 
	find "$SCRIPT_PATH" -name \#\*\# -print -type f -exec rm {} \; ;
	find "$SCRIPT_PATH" -name .#\* -print -exec rm {} \; ;   
}


echo 1/ cleaning ...
clean;

echo 1/ mvn Clean
mvn clean
test "$?" "mvn Clean";


echo 2/ git add --all .
git add --all .
test "$?" "git add --all";

echo 3/ git commit -a --allow-empty -m "" --allow-empty-message
git commit -a --allow-empty -m "" --allow-empty-message
test "$?" "git commit";

echo 4/ git push origin master
git push origin master
test "$?" "git push origin master";


