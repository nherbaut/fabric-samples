#!/bin/bash
CURRENT_VERS=$(cat ./version)
NEXT_VERS=$((CURRENT_VERS+1))
SEQUENCE=$((NEXT_VERS+1))
echo $NEXT_VERS > ./version


cd organization/magnetocorp
source ./magnetocorp.sh
#peer lifecycle chaincode package cp.tar.gz --lang node --path ./contract --label cp_0
cd contract-java
./gradlew --console plain build assemble </dev/null
cd ..
peer lifecycle chaincode package papercontract-0.0.1.jar --lang java --path contract-java/build/libs --label cp_$NEXT_VERS
peer lifecycle chaincode install papercontract-0.0.1.jar
export PACKAGE_ID=$(peer lifecycle chaincode queryinstalled|grep "Package ID"|sed -rn "s/.*: (cp_$NEXT_VERS.*), Label:.*/\1/p")
peer lifecycle chaincode approveformyorg --orderer localhost:7050 --ordererTLSHostnameOverride orderer.example.com --channelID mychannel --name papercontract -v 0 --package-id $PACKAGE_ID --sequence $SEQUENCE --tls --cafile $ORDERER_CA

cd ../..
cd organization/digibank
source ./digibank.sh
#peer lifecycle chaincode package cp.tar.gz --lang node --path ./contract --label cp_0
#peer lifecycle chaincode install cp.tar.gz
peer lifecycle chaincode package papercontract-0.0.1.jar --lang java --path ../magnetocorp/contract-java/build/libs --label cp_$NEXT_VERS
peer lifecycle chaincode install papercontract-0.0.1.jar
export PACKAGE_ID=$(peer lifecycle chaincode queryinstalled|grep "Package ID"|sed -rn "s/.*: (cp_$NEXT_VERS.*), Label:.*/\1/p")
peer lifecycle chaincode approveformyorg --orderer localhost:7050 --ordererTLSHostnameOverride orderer.example.com --channelID mychannel --name papercontract -v 0 --package-id $PACKAGE_ID --sequence $SEQUENCE --tls --cafile $ORDERER_CA


peer lifecycle chaincode commit -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --peerAddresses localhost:7051 --tlsRootCertFiles ${PEER0_ORG1_CA} --peerAddresses localhost:9051 --tlsRootCertFiles ${PEER0_ORG2_CA} --channelID mychannel --name papercontract -v 0 --sequence $SEQUENCE --tls --cafile $ORDERER_CA --waitForEvent

exit 0

cd ../..
cd organization/magnetocorp
rm -rf identity
cd application
node enrollUser.js
node issue.js

cd ../../digibank
rm -rf identity
cd application
node enrollUser.js
node buy.js
node redeem.js

beep
