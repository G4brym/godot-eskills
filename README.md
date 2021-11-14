# Godot Eskills

This is a simple Godot Android plugin that allows you to integrate your game with Catappult.io eskills platform.

## Installation & Usage

1. Enable [Android Custom Build](https://docs.godotengine.org/en/stable/getting_started/workflow/export/android_custom_build.html) for your project
2. Grab and extract the latest binary and plugin config from the [releases tab](https://github.com/G4brym/godot-eskills/releases) into `res://android/plugins/`
3. In your Android export settings, make sure the GodotEskills plugin is enabled

Now you can get the Intent URL using the `AppLinks` singleton when your game starts:

```gdscript
if Engine.has_singleton('GodotEskills'):
	var godoteskills = Engine.get_singleton('GodotEskills')
	godoteskills.startMatch()
```


## Compiling manually

1. Open this project in Android Studio
2. Grab the matching `godot-lib.<version>.<target>.aar` from the [Godot Engine downloads page](https://godotengine.org/download)
3. Add the downloaded `godot-lib.<version>.<target>.aar` as an Android Library, [see here](https://developer.android.com/studio/projects/android-library#AddDependency)
4. In Android Studio, click Build > Make Project
5. Once finished, you'll find the generated `aar` file in `./app/build/outputs/aar`
