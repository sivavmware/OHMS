#!/bin/bash

# *********************************************************************************
#                           variable declarations
# *********************************************************************************
# Directory where all shell scripts will reside e.g hms_oob_upgrade.sh, hms_ib_upgrade.sh
declare HMS_SCRIPT_DIR;
declare HMS_OOB_BACKUP_SCRIPT_FULLPATH;
declare HMS_OOB_SERVICE_SCRIPT_FULLPATH;
declare HMS_OOB_UPGRADE_SCRIPT_FULLPATH;
declare HMS_OOB_RECOVER_SCRIPT_FULLPATH;
declare HMS_OOB_BINARY_FILENAME;
declare HMS_OOB_BINARY_LOCATION;
declare HMS_OOB_BINARY_FULLPATH;
declare HMS_OOB_BINARY_CHECKSUM;
declare HMS_OOB_BINARY_CHECKSUM_FULLPATH;

# *********************************************************************************
#                           variable initializations
# *********************************************************************************

# Hms oob Backup script file
HMS_OOB_BACKUP_SCRIPT="hms_oob_backup.sh"

#Hms oob Service Script file that will start stop and query running HMS OOB
HMS_OOB_SERVICE_SCRIPT="hms_oob_service.sh"

#Hms oob Upgrade Script
HMS_OOB_UPGRADE_SCRIPT="hms_oob_upgrade.sh"

#Hms oob recover script that will recover from the previous running build.
HMS_OOB_RECOVER_SCRIPT="hms_oob_recover.sh"

# Function loginfo to log info message
loginfo()
{
	dtstamp=`date "+%Y-%m-%d %H:%M:%S"`

	if [ -z $2 ]
	then
		echo "$dtstamp $1"
	else
		echo "$dtstamp $1 [$2]"
	fi
}

# Function to check and set if any hmsbuild token has been passed while creating it
function check_arguments()
{
	loginfo "#######################################################";
	loginfo "### HMS_OOB_VALIDATE : 0. Configuring variables ###";
	loginfo "#######################################################";
	if [ "$#" -ge 8 ]
	then

		HMS_SCRIPT_DIR="$1";

		HMS_OOB_BACKUP_SCRIPT="$2";
		HMS_OOB_SERVICE_SCRIPT="$3";
		HMS_OOB_UPGRADE_SCRIPT="$4";
		HMS_OOB_RECOVER_SCRIPT="$5"

		HMS_OOB_BACKUP_SCRIPT_FULLPATH="${HMS_SCRIPT_DIR}/${HMS_OOB_BACKUP_SCRIPT}"
		HMS_OOB_SERVICE_SCRIPT_FULLPATH="${HMS_SCRIPT_DIR}/${HMS_OOB_SERVICE_SCRIPT}"
		HMS_OOB_UPGRADE_SCRIPT_FULLPATH="${HMS_SCRIPT_DIR}/${HMS_OOB_UPGRADE_SCRIPT}"
		HMS_OOB_RECOVER_SCRIPT_FULLPATH="${HMS_SCRIPT_DIR}/${HMS_OOB_RECOVER_SCRIPT}"

		HMS_OOB_BINARY_FILENAME="$6";
		HMS_OOB_BINARY_LOCATION="$7";
		HMS_OOB_BINARY_FULLPATH="${HMS_OOB_BINARY_LOCATION}/${HMS_OOB_BINARY_FILENAME}";

		HMS_OOB_BINARY_CHECKSUM="$8";
		HMS_OOB_BINARY_CHECKSUM_FULLPATH="$HMS_OOB_BINARY_LOCATION/$HMS_OOB_BINARY_CHECKSUM";

	else
		loginfo "FAILED: Required number of arguments must be passed before continuing.";
		return 10;
	fi
}

# Validation Entry Point
function validate()
{
	loginfo "###########################################";
	loginfo "### HMS_VALIDATE : 1. Validation Phase ###";
	loginfo "############################################";

	echo
	loginfo "###################################################";
	loginfo "### HMS_VALIDATE : 1.1 HMS OOB Validation Phase ###";
	loginfo "###################################################";
	validate_hms_oob || {
		loginfo "Unable to validate HMS_OOB files";
		return 10;
	}
	loginfo "HMS OOB Validation completed Successfully";

}

# Validation of HMS OOB files
function validate_hms_oob()
{
	loginfo "1.1.1 Validating HMS-OOB Script file"
	validate_hms_oob_script || {
		loginfo "HMS_OOB Script not found";
		return 11;
	}

	loginfo "1.1.2 Validating HMS-OOB Binary"
	validate_hms_oob_binary || {
		loginfo "HMS_OOB Binary [ $HMS_OOB_BINARY_FULLPATH ] not found";
		return 12;
	}

	#loginfo "1.1.3 Validating HMS-OOB Binary against checksum"
	#validate_hms_oob_binary_checksum || {
	#	loginfo "Unable to validate Checksum for HMS OOB Binary";
	#	return 13;
	#}
}

# Check Hms oob script availability
function validate_hms_oob_script()
{
	# check if latest hms oob script file exists
	local oobscript="$HMS_OOB_BACKUP_SCRIPT_FULLPATH"
	[[ -f "$oobscript" ]] || return 90

	local oobscript="$HMS_OOB_SERVICE_SCRIPT_FULLPATH"
	[[ -f "$oobscript" ]] || return 91

	local oobscript="$HMS_OOB_UPGRADE_SCRIPT_FULLPATH"
	[[ -f "$oobscript" ]] || return 92

	local oobscript="$HMS_OOB_RECOVER_SCRIPT_FULLPATH"
	[[ -f "$oobscript" ]] || return 93

}

# Check Hms oob binary availability
function validate_hms_oob_binary()
{
	loginfo "Checking OOB binary [ $HMS_OOB_BINARY_FILENAME ] in location [ $HMS_OOB_BINARY_LOCATION ]"
	local oobbinary="$HMS_OOB_BINARY_FULLPATH"
	[[ -f "$oobbinary" ]] || return 102
}

# Validate Hms oob binary against provided checksum
function validate_hms_oob_binary_checksum()
{
	loginfo "Checking md5 checksum file [ $HMS_OOB_BINARY_CHECKSUM ] in $HMS_OOB_BINARY_LOCATION"
	# check if latest hms oob binary checksum file exists
	local f="$HMS_OOB_BINARY_CHECKSUM_FULLPATH"
	[[ -f "$f" ]] || {
		loginfo "HMS OOB Binary Checksum file [ $HMS_OOB_BINARY_CHECKSUM_FULLPATH ] not found";
		return 103;
	}

	validate_checksum $HMS_OOB_BINARY_FULLPATH $HMS_OOB_BINARY_CHECKSUM_FULLPATH || {
		return 104;
	}
}

# Validate Checksum for file with CheckSum Provided. Usage: validate_checksum <binary fullpath> <provided checksum file>
function validate_checksum()
{
	if [ "$#" -eq 2 ]
	then
		md5prog=$(get_md5sum_fullpath);
		binary_fullpath="$1";
		provided_checksum_file="$2";

		if [ -z $md5prog ]
		then
			loginfo "Unable to find md5sum program on this system.Can not continue."
			return 50;
		fi

		calc_md5=`${md5prog} ${binary_fullpath} | awk '{ print $1 }'`;
		if [ -z $calc_md5 ]
		then
			{ loginfo "Error in calculating MD5 Checksum for file [ $binary_fullpath ]"; return 51; }
		fi

		orig_md5=`cat ${provided_checksum_file} | awk '{ print $1 }'`;
		if [ -z $orig_md5 ]
		then
			{ loginfo "Error in reading provided MD5 Checksum file [ $provided_checksum_file ]"; return 52; }
		fi

		# removing trailing and heading whitespaces before comparison
		calculated_md5=`trim_trailing_whitespaces $calc_md5`
		original_md5=`trim_trailing_whitespaces $orig_md5`

		# for case insensitive md5 comparison
		if [ `echo "$calculated_md5" | tr [:upper:] [:lower:]` =  `echo "$original_md5" | tr [:upper:] [:lower:]` ];
		then
			loginfo "Checksum SUCCESS. calculated [ $calculated_md5 ] and provided [ $original_md5 ]"
			return 0;
		else
			loginfo "Checksum FAILED. calculated [ $calculated_md5 ] and provided [ $original_md5 ]"
			return 53;
		fi

	else
		loginfo "Incorrect arguments to use the function No. of arguments passed [ "$#" ]";
	fi
}

# Gets the absolute path of md5sum on the system
function get_md5sum_fullpath()
{
	echo `which md5sum`;
}

#Removes Trailing and heading whitespaces
function trim_trailing_whitespaces()
{
	#var1="\t\t Test String trimming   "
	var1=$1
	Var2=$(echo "${var1}" | sed 's/^[[:space:]]*//;s/[[:space:]]*$//')
	echo $Var2
}

# Log & Update HMS Validation Status
function fatal_exit()
{
	loginfo "$1 Exit Code: $2"

	echo
	echo "***************************************************************"
	echo "HMS Validation Phase: Failed @ `date`"
	echo "***************************************************************"
	exit $2
}


# MAIN ENTRY POINT

# 0. Check Arguments first to see if any HMS Token has been provided.
check_arguments "$@" || {
fatal_exit "HMS Arguments Check: Failed" $? NO_UNDO_ACTION;
}

validate || {
fatal_exit "HMS upgrade validation failed" $? NO_UNDO_ACTION;
}

echo
echo "***********************************************************************"
echo "HMS Validation completed successfully. All preconditions satisfied. @ `date`"
echo "***********************************************************************"

exit 0;
