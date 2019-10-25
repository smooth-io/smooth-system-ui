# Smooth system UI

Hassle free dealing with Android System UIs (Status bar, Navigation bar, Action bar) With Automated lifecycle managment for Fragments and Activies, and automated handling of configuration changes.

## Getting Started

The library has two versions, with and without lifecycle management, 
use the lifecycle one to have an automated disabling/releasing of resources
and changes when the fragment or activity is dead and you need to revert the changes

### Installing

##### Without lifecycle (__NOT RECOMMENED__) : 
```
implementation "io.getsmooth.kt.android.system_ui:1.0.0" 
```

##### With lifecycle (__RECOMMENED__) : 
```
implementation "io.getsmooth.kt.android.system_ui:1.0.0" 
implementation "io.getsmooth.kt.android.system_ui.lifecycle:1.0.0" 
```

## Usage

If you use the varient with __auto__ then you don't have to do anything.

but using the varient without __auto__ you will have to call __disable__ in __onStop__.

Example __without__ Auto:
```
private var fullScreen: FullScreen? = null
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    fullScreen = fullScreen {}
}

override fun onStop() {
    super.onStop()
    fullScreen?.disable()
}
```
Example __with__ Auto: Requires lifecycle dependency
```
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    fullScreenAuto{} //Requires lifecycle dependency
}
```

#### Varients : 
__Normal mode__
```
normalAuto(0//optional defaults to 0) {//This makes the status bar and navigation bar visible

            //Passing -1 to delay will not make any change
            //Passing 0 to delay will make the change immediate
            //Passing a value larger than 0 will make the change after the specified delay mills
            
            lightStatusBar() //Make status bar icons dark (use it if you have light status bar)
            
            darkStatusBar() //Make status bar icons light (use it if you have dark status bar)
            
            autoDelay(3000) //This is the delay applied when a configuration change happens and we need to restore the requested state
}
```
__Full screen mode__
```
fullScreenAuto{//This means lean mode
            //The default in fullscreen is hide the action bar
            showActionBar() //Show the action bar instead of hiding it
            
            //The default in full screen is hiding the navigation bars also
            showNavigationBar() //Show the navigation bar instead of hiding them
            
            //The default is that the layout reacts to system UIs visibility like going down/up 
            //based on system UIs visibility
            keepLayout() //Don't react to system UIs changes and keep your layout stable/not changing
}
```
__Immersive mode__
```
immersiveAuto{
            //The default is not_sticky 
            sticky()  //Makes the screen sticky with immersive mode
            
            //The default in immersive mode is hide the action bar
            showActionBar() //Show the action bar instead of hiding it
            
            //The default is that the layout reacts to system UIs visibility like going down/up
            //based on system UIs visibility
            keepLayout() //Don't react to system UIs changes and keep your layout stable/not changing
}
```

__Common things__
Those available in all varients 
##### You can always pass a delay to your varient :
Passing -1 to delay will not make any change
Passing 0 to delay will make the change immediate
Passing a value larger than 0 will make the change after the specified delay mills

##### Light/Dark status bar: 
If you want Light Status bar : call lightStatusBar()
If you want Dark status bar : call darkStatusBar()

##### Auto delay:
This determines when a configuration changed and the system ui in a state not the one you requested, this is the delay applied before the changes happen and the ui is back to the state you requested, defaults to 1 second (1000 mills).

## Contributing

Feel free to submit pull requests, and we will review it and accept it if it's working well.

## Versioning

We use [SemVer] (http://semver.org/) for versioning. For the versions available, see the [tags on this repository] (https://github.com/smooth-io/smooth-system-ui/tags). 

## Authors

* **Mohammed Alhammouri** - *Initial work* - [Mohammed Alhammouri](https://github.com/mhammouri98)


## License

This project is licensed under the Apache License - see the [LICENSE.md](LICENSE.md) file for details

