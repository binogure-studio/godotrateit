# godot-rateit

[![Platform](https://img.shields.io/badge/Platform-Android-green.svg?longCache=true&style=flat-square)](https://github.com/xsellier/godotrateit)
[![Godot Engine](https://img.shields.io/badge/GodotEngine-2.1-orange.svg?longCache=true&style=flat-square)](https://github.com/godotengine/godot)
[![LICENCE](https://img.shields.io/badge/License-MIT-green.svg?longCache=true&style=flat-square)](https://github.com/xsellier/godotrateit/blob/master/LICENSE)

Allows you to open the google play store on your application in order to rate it.

# Usage


## Loading the module

Edit `engine.cfg` and add an `android` part as following:

```ini
[android]
modules="org/godotengine/godot/GodotRateIt"
```

## Initializing the module using GDScript

Here is an example

```python
extends Node

onready var godot_rateit = Globals.get_singleton('GodotRateIt')

func rate_it():
  if OS.get_name() == 'Android' and godot_rateit != null:
    godot_rateit.rate()
```

# License

[See LICENSE file](./LICENSE)
