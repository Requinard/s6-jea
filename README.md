# Kwetter

Reimplementing twitter for fontys because I have too much time on my hands

## How to build

Make sure  you have payara running, with a `psql` jdbc datapool and `kwetter_realm` JAAS context.

- `gradle build`
- upload the built artifact in `build/libs/` to payara
- Boom, it's done
