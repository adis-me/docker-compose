# 🔲 Demo App

_Demo app_

## Prerequisites

- You have JDK 17 installed
- You have a running MySQL instance
- You need to generate a private and public key for JWT
  - Create keypair: `openssl genrsa -out keypair.pem 4096`
  - Extract public key: `openssl rsa -in keypair.pem -pubout -out publickey.crt`
  - Extract private key: `openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out pkcs8.key`

## Run

Two options:

1. Run from the IDE (do not forget to specify `local` profile).
2. Run with gradle: (default profile; `local`) `./gradlew bootRun`. Or specify custom
   profile: ` ./gradlew bootRun -P{dev|local|prod|acc}`.

## Test

`./gradlew test'
---
Maintained by FlinkSoftware [development@flinksoftware.nl](mailto:development@flinksoftware.nl)