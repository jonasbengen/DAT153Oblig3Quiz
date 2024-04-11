# Oblig 3 - Quiz App Revision

## Changelog from Oblig 1
- Removed sharing data from project internal datastructure to a Roomdatabase on the running device
- Persist score, photo, and answers on screen rotation.
- Espresso tests for the functionality

## Answers to the questions

In the output after running the command `./gradlew connectedAndroidTest --info` I found the following:
- the build uses build.gradle file to configure the build on project level and then for app level
- it selects the primary task to run **connectedAndroidTest**
- it creates a list of tasks to run (checks, debugging, etc.)
- the actual testing task is **:app:connectedDebugAndroidTest**
- it states how many tests it will run and on what device: **Starting 7 tests on Medium_Phone_API_34(AVD) - 14**
- then it will give a summary and generate a XML file: **[XmlResultReporter]: XML test result file generated at /path/to/project/app/build/outputs/androidTest-results/connected/debug/TEST-Medium_Phone_API_34(AVD) - 14-_app-.xml. Total tests 7, passed 7**

APK used for testing is located in **app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk**.
Decoding it with **apktool** `apktool decode app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk` gave the following structure:
```
app-debug-androidTest/
├── AndroidManifest.xml
├── apktool.yml
├── META-INF
├── original
├── res
├── smali
├── smali_classes2
└── smali_classes3
```

The AndroidManifest.xml tells us:
- compile sdk version and version code name
- platform build version code and name
- package name
- instrumentation setup for AndroidJUnitRunner
- queries that APK uses during testing
- permissions that APK uses - **REORDER TASKS**
- activities that APK uses for testing

The apktool.yml tells us more about the APK structure, like:
- version of apktool used
- apk file name
- used framework
- min and target sdk version
- package info
- version info
- if resources were compressed
- etc.

## ADB-commands
- `adb install /path/to/your_app.apk` - installs the application on the started emulated android device
- `adb shell am instrument -w com.example.DAT153Oblig3Quiz/android.test.runner.AndroidJUnitRunner`
   - `-w` flag forces `am instrument` to wait til the instrumentations are finished before it terminates itself. This keeps the shell open until the tests are finished and are required to get the output from the tests
   - Then we enter the packagename of the tests and enter the Runner class of the test which is often `andoridx.test.runner.AndroidJUnitRunner`

## Test description

The UI tests are located in **androidTest** directory. There are 3 files:
```
GalleryTests.java
MainTests.java
QuizTests.java
```

### Main Tests
There is a Rule that launches the activity before each test. It uses *IntentTestRule*
Then there are 2 tests:
1. **TestQuizButton** - find the Quiz-button on the screen and click it, then check if the QuizActivity was opened
2. **TestGalleryButton** - find the Gallery-button on the screen and click it, then check if the GalleryActivity was opened

Openings are checked by using `intended()`

### Gallery Tests
There is a Rule that launches the activity before each test. It uses *IntentTestRule*
Then there are 2 tests:
1. **TestDeletePhotoo** - get current number of items in the gallery, clicks the delete-button on the first item, clicks "YES" in the alert dialog, check if number of items went down by 1
2. **TestAddPhoto** - get current number of items in gallery, click the add-image-button, write in description for the photo, mocks a photo from the project resources, clicks the button for uploading the photo, clicks the save-button then the back-button to get back to the gallery, check if number of items went up by 1

Intended number of items in the gallery is checked by using `assertEquals(before, after - 1);` and `assertEquals(before, after + 1);`

### Quiz Tests
There is a Rule that launches the activity before each test. It uses *IntentTestRule*
Then there are 2 tests:
1. **TestScoreRightAnswer** - get the correct answer, clicks the button with this answer and checks for the intended score text on the screen ("Score: 1 / 1")
2. **TestScoreWrongAnswer** - get the correct answer, clicks a button with a wrong answer and checks for the intended score text on the screen ("Score: 0 / 1")

Intended text on screen is checked by using `onView(withId(R.id.score)).check(matches(withText("Score: 0 / 1")));` and `onView(withId(R.id.score)).check(matches(withText("Score: 1 / 1")));`

QuizActivity had to be modified to allow for testing, I added getter for getRightAnswer and getAllButtonTexts

