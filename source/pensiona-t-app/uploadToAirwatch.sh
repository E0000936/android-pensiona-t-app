#!/bin/bash

echo Uploading android blob
: <<'END_COMMENT'
tenant="/DbYzsxfUyP4367iiVVflZJDJ7I6vWs8A+2cdaKyF5c="
auth="cHJvdmVlZG9yOmFwazIwMTkx"
#ipafile="build/Release-iphoneos/devopsTest.ipa"
ipafile=$1

content=$(curl -X POST  -H "aw-tenant-code: $tenant"  -H "Authorization: Basic $auth" -H 'Accept: application/json;version=1' -H 'Content-Type: application/octet-stream' -H 'Expect: 100-continue' --data-binary "@$ipafile" "https://as257.awmdm.com/api/mam/blobs/uploadblob?filename=Pensiona-T-staging.apk" )

echo ${content}

echo Publishing android..

errorCode=$(echo ${content} | ./jq -r '.errorCode')
message=$(echo ${content} | ./jq -r '.message')
value=$(echo ${content} | ./jq -r '.Value')

echo Error ${errorCode}
echo Message ${message}
echo Value ${value}

content=$(curl -k -v -X POST -H 'Authorization: Basic cHJvdmVlZG9yOmFwazIwMTkx' \
	-H 'aw-tenant-code: /DbYzsxfUyP4367iiVVflZJDJ7I6vWs8A+2cdaKyF5c=' \
	-H "Accept: application/json" -H "Content-Type: application/json" \
	-d '{"ApplicationName":"ProfuturoAsesores", "AutoUpdateVersion":true, "BlobId":'$value', "PushMode":"Auto", "DeviceType":"Android",
"SupportedModels":{"Model":[{"ApplicationId":139, "ModelId":2, "ModelName":"Android"}]}}' \
	https://as257.awmdm.com/api/mam/apps/internal/begininstall)

echo ${content}
$SHELL
END_COMMENT
