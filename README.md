# Tracker kun [![Telegram](https://img.shields.io/badge/telegram-bot-blue.svg)](http://telegram.me/TrackerKun_bot)
[![Build Status](https://travis-ci.org/one-commit-men/manga-observer-bot.svg)](https://travis-ci.org/one-commit-men/manga-observer-bot)

###Simple telegram bot to track manga chapters

Currently, you can use manga from this sources:
- ReadManga [ru]
- MintManga [ru]

If you found some bug\want to add new source - notify me in telegram [@ddovgal](http://telegram.me/ddovgal)

###How to run:
- `git clone`
- `mvn clean package`
- `cd target/bin`
- run `main` or `main.bat` if Windows

**NOTE**: you need to define your own `BOT_TOKEN` and `BOT_USERNAME` in `BotConfig.kt`  
*In release archives it will be complete, generated `main` file, so you will not need to use Maven*

###Working with docker:
As a first step - install docker for your platform

#####To run program from docker image, you must write 2 commands in docker's CLI
- `docker build -t [image-name] https://github.com/one-commit-men/manga-observer-bot.git`
- `docker run [image-name] --env BOT_TOKEN=[bot-token] --env CREATOR_CHAT_ID=[creator-id] --env DATABASE_URL=[database-url] --env LOG4J_LOGGER_BOT_TOKEN=[bot-token]`
    
    - <sup>`BOT_TOKEN` is a token of your bot from BotFather
    - <sup>`CREATOR_CHAT_ID` is a id of chat, where Log4J will send errors
    - <sup>`DATABASE_URL` is a url of database, where all the bot's data will exist
    - <sup>`DATABASE_URL` is same as a `BOT_TOKEN`, but for Log4J reports bot<sub><sup>

#####To just compile program and get the result, you must write different second command in docker's CLI
- `docker run -it -v [result-destination]:/usr/src/disk [image-name] cp -R ./../.. /usr/src/disk`
After this, you will have compiled, ready to work program on your local `[result-destination]` folder
