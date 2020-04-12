# ColorStrings

A light weight library for simple DropDown Solution with TextView 

## How to
To get this project into your build:
### Step 1
Open project level gradle file
Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```java
allprojects {
    repositories {
    	...
    	maven { url 'https://jitpack.io'}
    }
}
```
### Step 1
Open app module gradle file and add the dependency
```java
dependencies {
	implementation 'com.github.UmairKhalid786:ColorStrings:1.0.1'
}
```
### How to use it ?
After following above mentioned steps you are ready to code , Sample code is pasted from example given in this repo.
```xml
 <com.techlad.dropdowntextview.DropDownTextView
        android:id="@+id/optionsTv"
        style="@style/DropDownTv"
        android:padding="16dp"
        android:hint="Select Options"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"  />
```

```kotlin
    val arrayList = arrayListOf<String>()

        arrayList.add("Option 1")
        arrayList.add("Option 2")
        arrayList.add("Option 3")

        optionsTv.setOptions(arrayList)
```
### Output
[![smoutput](https://ibb.co/jX2Tzy "smoutput")](https://ibb.co/jX2Tzy "smoutput")
