## AndroidAppUpdate

### What is it?

A fully functional sample for updating your app from an arbitrary external source, other than GooglePlay.

### Requirements

Will reliably work for app over API level 21.

### How it works

This method uses the ```version_code``` attribute in ```build.gradle``` for labeling versions of the app. Basically, the app will offer to update if the version code of the app on the web is higher than the ```version_code``` of the version installed on your phone.
To use this project, you should copy the code to your own project. Note the following things:

#### First setup
- ```GGlobals.java``` contains two constants: 
    - ```LATEST_APK``` - the address of your ```.apk``` (x.apk), and
    - ```LATEST_VERSION_ADDRESS``` - the address of the latest version code (x.ver).
- Thus you should set these constants to two resources accessible from the internet:
    - **Latest version code** - should be a text file containing only the number. You will have to keep updating this manually each time you release a new version of your app.
    - **Latest app ```.apk```** - should be the latest actual apk that the users install.

#### After every new release of your app, you

- Increase the ```version_code``` in ```build.gradle```
- Upload the new ```.apk``` on the same address ```LATEST_APK```
- Update the number in the file located on address ```LATEST_VERSION_ADDRESS``` to fit the new version code

After this, all of your users that still have the old version of your app can be prompted to install the new version.


#### Shortfalls

This is both an advantage and a shortfall. When we offer to update, the user will always be asked to permit installing the app from an external source.