# Kwetter

Reimplementing twitter for fontys because I have too much time on my hands

[![Build Status](https://travis-ci.org/Requinard/s6-jea.svg?branch=master)](https://travis-ci.org/Requinard/s6-jea)

See [Gitlab](https://gitlab.com/Requinard/s6-jea/pipelines) for the build status with artifacts

## How to build

Make sure  you have payara running, with a `psql` jdbc datapool and `kwetter_realm` JAAS context.

- `gradle build`
- upload the built artifact in `build/libs/` to payara
- Boom, it's done

## User stories

User stories an be found in `docs/userstories.md`

## Architecture

A handwritten diagram can be found in `docs/architecture.jpg`

## Optionals implemented

For the backend

- CDI Interceptors to censor a tweet
- A build enviroment (Travis and Gitlab)
