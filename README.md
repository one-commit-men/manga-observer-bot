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
