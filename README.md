# Borescope

Simple plugin that init stream connection with borescopes on a simple widget for flutter

### Features
- Provides a Image widget with actual borescope image
- Provides a Base64 String image with actual borescope image
- Check SSID connection to verify if is a valid borescope  wifi
### To do
- Take picture

## Getting Started
- Initialize the borescope controller on initState
```
@override
void initState() {
  super.initState();
  controller.initBorescope().then((value) {
    if (value != "success") {
      //Error treatment here
    }
  });
}
```
- You also need to handle the update system the recomended way is to using runningChanged and imageChanged, the runningChanged is called when the borescope is initialized or disposed and the imageChanged is every time the borescope plugin receives a new image from the borescope
- Create the setState function for updating the application
```
void updateWidget() {
    setState(() {});
}
```
- Add the updateWidget function in the initState before the initBorescope function
```
@override
void initState() {
  super.initState();
  controller.runningChanged = () => updateWidget();
  controller.imageChanged = () => updateWidget();
}
```
- Now you can handle the Widget Build
```
SizedBox(
  width: 200,
  height: 200,
  child: !controller.isRunning ? CircularProgressIndicator() : controller.image,
),
```
- And finally you can add a dispose controller in dispose method
```
@override
void dispose() {
  super.dispose();
  controller.disposeBorescope();
}
```

