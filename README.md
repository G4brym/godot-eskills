# Godot Eskills

This is a simple Godot Android plugin that allows you to integrate your game with Catappult.io eskills platform.

## Installation & Usage

1. Enable [Android Custom Build](https://docs.godotengine.org/en/stable/getting_started/workflow/export/android_custom_build.html) for your project
2. Grab and extract the latest binary and plugin config from the [releases tab](https://github.com/G4brym/godot-eskills/releases) into `res://android/plugins/`
3. In your Android export settings, make sure the GodotEskills plugin is enabled

Now you can get the Intent URL using the `GodotEskills` singleton when your game starts:

```gdscript
var eskills

func _ready():
    if Engine.has_singleton("GodotEskills"):
        eskills = Engine.get_singleton("GodotEskills")

        # These are all signals supported by the API
        # You can drop some of these based on your needs
        eskills.connect("payment_started", self, "_on_payment_started") # No params
        eskills.connect("payment_error", self, "_on_payment_error") # Response resultCode (int), message (string)
        eskills.connect("match_found", self, "_on_match_found") # No params

        eskills.findRoom({
            value="1",
            currency="USD",
            product="1v1",
            timeout="200",
            domain="com.your.app",
            environment="SANDBOX",
            number_of_users="2",
            user_name=$"Username".text,
            user_id=$"Username".text,
        })
        
        # After a match is started you can get the other player's and room details with this
        # You should also use this method to periodicaly check if the game has already finished
        eskills.getRoom()
        
        # Update the scores
        eskills.updateScore(12, "PLAYING")
        
        # If you need to make any additional request that is not covered by this lib you can 
        # get the session token with this
        eskills.getSessionToken()

    else:
        print("Android Eskills support is not enabled. Make sure you have enabled 'Custom Build' and the GodotEskills plugin in your Android export settings! Eskills will not work.")
```


## Compiling manually

1. Open this project in Android Studio
2. Grab the matching `godot-lib.<version>.<target>.aar` from the [Godot Engine downloads page](https://godotengine.org/download)
3. Add the downloaded `godot-lib.<version>.<target>.aar` as an Android Library, [see here](https://developer.android.com/studio/projects/android-library#AddDependency)
4. In Android Studio, click Build > Make Project
5. Once finished, you'll find the generated `aar` file in `./app/build/outputs/aar`
