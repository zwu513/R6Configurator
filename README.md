# R6Configurator

A GUI-based program that allows you to manually select a server and limit your fps.
There will be bugs, report the under issues or [here](https://forms.gle/hsuAxzucgdovZ4vs9). [Ubisoft support article](https://www.ubisoft.com/en-us/help/rainbow-six-siege/article/selecting-data-centres-manually-in-rainbow-six-siege-pc/000060594) explaining how this works.


### FAQ
**How does this work?**
- It changes certain lines in Seige's settings file.

**How do I use this?**
- Run the JAR or unzip the folder and start RunThis.exe. Hit the "Select settings file" button and you will see the folder(s) that are named with your user ID. Open the folder and select the settings.ini file. If you have multiple accounts, use [Tabwire](https://tabstats.com/siege/) to check your user ID. Search your username and look at the end of the URL. For example, if you're [Beaulo](https://tabstats.com/siege/player/3cc51897-49c4-45f6-af9d-66507b8ef0e1), your ID would be 3cc51897-49c4-45f6-af9d-66507b8ef0e1. 

**Why do my settings remain unchanged in-game?**
- This might be because Ubisoft connect is using the settings file you have synced to the cloud. You can force Siege to use your local settings file by making it read-only. Your settings file will be in documents >> My games >> Rainbow Six -Siege to find the folders. Go to the settings file >> properties >> general and look at the last line which has a checkbox for reading only. If the checkbox is filled, the file is read-only. Uncheck it when  

**Why does selecting [server] not work?** 
- I have no idea. Ubi's list of data centers in the settings file and support page are different. If you find that a server consistent does not work, submit an issue with the form above.

 **Is this bannable?**
- No because it only edits a file that [Ubisoft allows you to edit](https://www.ubisoft.com/en-us/help/rainbow-six-siege/article/selecting-data-centres-manually-in-rainbow-six-siege-pc/000060594).
