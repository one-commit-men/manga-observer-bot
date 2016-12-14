# Tracker kun [![Telegram](http://www.sdam66.ru/images/telegram-logo.png)](http://telegram.me/TrackerKun_bot)
[![Build Status](https://travis-ci.org/one-commit-men/manga-observer-bot.svg?branch=dev)](https://travis-ci.org/one-commit-men/manga-observer-bot)

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
######To run program from docker image, you must write 2 commands in docker's CLI
- `docker build -t [image-name] [link-to-gihub]` (https://github.com/one-commit-men/manga-observer-bot.git#dev)
- `docker run [image-name]`
######To just compile program and get the result, you must write different second command in docker's CLI
- `docker run -it -v [result destination]:/usr/src/disk [image-name] cp -R ./../.. /usr/src/disk`
After this, you will have compiled, ready to work program on your local `[result destination]` folder
