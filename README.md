# Goliath Envious FX
GUI Linux Nvidia GPU overclocking utility written in JavaFX

## Download
You can download from the [releases](https://github.com/BlueGoliath/GoliathEnviousFX/releases) page.

## Requirements

* Java 11 or newer
* nvidia-smi
* nvidia-xconfig
* nvidia-settings

## Support
Only desktop GeForce GTX and RTX GPUs are offially supported by this application but other Nvidia GPUs should still work.
Only the latest LTS/normal/Vulkan beta release drivers are supported.
If you have a GPU that is supported but doesn't work please make a bug report.

## Usage
Launch with 'java AppLauncher.java'. Launch with 'sudo' for power limit and coolbits control.

Use '--force' switch to force Goliath Envious
to allow usage regardless of whether the GPU actually supports a given feature. This DOES NOT magically make said feature available but overrides the API hint.
This does not allow coolbits control.

Use '--smionly' switch to not start the nvidia-settings update thread(lower CPU usage).
The following Nvidia attributes WILL NOT be updated:

* Performance Levels
* Current Performance Level
* PCIe Bandwidth Utilization
* Video Engine Utilization
* Video Encoder Utilization
* Video Decoder Utilization
* Core Clock Offset*
* Memory Clock Offset*
* Fan Mode*
* Fan Speed Target*
* Fan Speed RPM

*Will be updated after applying a new control value.

UI components that would normally update or react to changes to these Nvidia attributes will not update.

## Features(currently)
* Easily get supported Nvidia Settings CLI Attribute readouts
* Change PowerMizer mode
* Apply over or under clocks to graphics core and memory
* Increase voltage(not supported on Pascal or newer)
* Decrease or increase GPU power limit via nvidia-smi
* Power related readouts from Nvidia SMI
* Performance Limit reason from Nvidia SMI
* On Screen Display for GPU info while in-game*
* Performance Limit states
* Ability to set coolbits value(root required)
* and more

*Is just a transparent window and DOES NOT integrate with the game itself.
In-game performance as a result varies wildy and SHOULD NOT be used in any actual competitve game matches.

## Known Issues

* nvidia-settings: Attribute updating is CPU intensive. You can use '--smionly' to reduce usage greatly.
* Goliath Envious FX: Use of the OSD can and will cause performance issues with games.
* nvidia-settings: setting a fan speed of 0 on a GPU that doesn't support turning off the fans(newer GPUs with secondary BIOS set) results in strange results. Data reported by Goliath Envious is accurate.
* Linux: Many Linux distros(Fedora, Arch Linux) do not package or install the Nvidia binary GPU driver correctly. If the drivers was installed using a GUI program you are *probably* fine. Please contact your Linux distro developers and ask them to package/install them correctly.
* Linux: Updating before and/or while running Goliath Envious FX can result in the application failing to work or launch. It shouldn't even be said that live system updating is *very* dangerous and should *never* be done under any normal circumstances. I've had a bogus bug report with GoliathOUFX as a result of this, stop doing it please.
* JavaFX Performance: JavaFX performance is very poor due to -Dprism.forceUploadingPainter being set to true. GPU Utilization is 2x to 2.5x higher when performing actions, higher GPU power draw, and PCIe utilization is extremely high as well.
* JavaFX GTK3 Bug: mouse transparency doesn't work correctly and text may be clickable in OSD. mouse transparency doesn't work at all in GTK3. Goliath Envious FX forces GTK 2 to attempt to fix this.
* JavaFX GTK3 Bug: window appears in the top-left or some other incorrect location then moves to center of screen. Fixed by setting GTK to 2.
* JavaFX GTK Bug: When maximized, the Maximized button will not reappear and disappear correctly when switching between resiable enabled/disabled.
* JavaFX TilePane/ScrollPane Bug: ScrollPane does not handle TilePane's height correctly and will ignore it. To fix this, ScrollPane's scrollbars are always visible.
* JavaFX Rendering Bug: Resizing a JavaFX application results in buffer resetting and other visual glitching. Using partial fix by setting -Dprism.forceUploadingPainter to true.
* JavaFX Rendering Bug/Performance: UI fails to keep up with resizing, resulting in white space in recently expanded areas. Making the window smaller results in UI components failing to keep up as well.
* JavaFX Rendering Bug: resizing Goliath Envious FX(and probably other applications, no one uses TableView apparently) or the SplitPane divider can result in horizontal scroll bars showing in TableView despite enough room being available to show content. Resizing the window again will force them to go away. Resizing from the bottom right results in it happening less often(affected by performance).
* JavaFX Rendering Bug: resizing a JavaFX application from the bottom-left, left, top-left, top, and top-right results in UI jittering.
* JavaFX Rendering Bug: setting resizable to false & maximized to true and launching when interacting with Gnome 3 overview(opening in particular) results in only the menu being rendered correctly. Application will fix itself if you enabled resizing in the menu and resize the window.
* JavaFX SplitPane Bug: divider does not move to requested position and will remain at the left of the application at launch. Requested position is 15%. Seems to be fixed in JavaFX 13(unreleased).
* JavaFX SplitPane Bug: attempting to set the preffered height of a SplitPane using SplitPane itself or any components within using Integer.MAX_INTEGER results in a out of heap memory error. Goliath Envious FX has it set to the highest height of all connected screens at launch(See Screen Java class). There isn't any easy way to keep watching for higher screens being plugged in so if you plug in a higher resolution screen you'll just need to restart.
* JavaFX SplitPane/rendering Bug: jittering when responding to position property change to force max divider position of 15%. JavaFX doesn't seem to be able to keep up with window resizing.
* JavaFX CSS Bug: in-between transion white space with content tree view when not maximized. Updating the component by clicking a tree item makes it go away.
* JavaFX CSS Bug: black number color when switching to content containing a Spinner component. Switching to other content and back will fix this. Most likely https://bugs.openjdk.java.net/browse/JDK-8088506.
* JavaaFX CSS Bug: clicking and holding various UI components(slider thumbs, scrollbar, etc) and alt-tabbing results in those UI components keeping the selected CSS styling even when/after clicking other components. Clicking the glitched component fixes it.
* JavaFX/Goliath Envious FX: various other minor CSS bugs with TableView column separator and button keyboard focus(hard to even notice the buttons unless you put your eyes directly to the screen). JavaFX's CSS documentation isn't well documented and I can't find any ways to fix these after hours of searching. They are *really* minor though.

## Disclaimer

As per the license, I accept no responsiblity for any damages that happen by using this utility.
