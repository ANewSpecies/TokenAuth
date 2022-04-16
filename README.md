<div align="center">

# TokenAuth
**A simple Minecraft Forge mod that allows logging in to other people's accounts using a username:uuid:token combo.**

</div>

> Fun fact: most of this readme was authored by GitHub Copilot.

## [Demo](https://www.youtube.com/watch?v=PHMRA5k4KM0&ab_channel=DxxxxY)
Ratting myself, and logging into my own account.

## Download
Setup and build yourself: Follow [1.8.9ForgeTemplate](https://github.com/DxxxxY/1.8.9ForgeTemplate) setup & build instructions.

or

Download from [Releases](https://github.com/DxxxxY/TokenAuth/releases) and drag that into your mod folder.

## Features
- Login into an account with a username:uuid:token combo.
- Restore your session to your original one (at launch time) with the click of a button.
- Shows the status (user, uuid) next to the gui button in GuiMultiplayer.
- Works well with [DxxxxY/R.A.T](https://github.com/DxxxxY/R.A.T).
- Error handling for invalid inputs.
- Simple:
    - No dependencies.
    - No config.
    - Uses Forge's event system to draw a button on the multiplayer gui.
    - Uses an AT to allow modification of the session on runtime.
    
## Disclaimer
This is for educational purposes only. I am not responsible for any damage caused by this tool.

## License
GPLv3 © dxxxxy