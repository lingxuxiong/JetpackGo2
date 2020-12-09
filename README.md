# Introduction
> *The Data Binding Library is a support library that allows you to bind
> UI components in your layouts to data sources in your app using a
> **declarative** format rather than programmatically.*

# Assumptions
- Android Studio IDE: v4.1.1
- Android Gradle Plugin: v4.1.1
- Kotlin & Plugin Version: 1.4.10

# Terms And Abbr.
- View Binding (VB)
> Binding layout views that has a unique ID to a generated file so that
> the views can be accessed handily. It can be replacement `findViewById` calls.

- Data Binding (DB)
> Binding layout views to model objects so that the bound objects can be
> changed directly from the layout, and vice versus, changes on the objects
> can be applied to the layout views automatically. Data Binding be one-way  
> (V -> M) or Two-way (V <-> M), depending on the implementation though
> Two-way binding is expected most of the time and it requires the model data
> observable and wrapped with `LiveData`.

# How to use
1. Enable dataBinding from the app gradle file
```groovy
plugins {
    // If you plan to use data binding in a Kotlin project, 
    // you should apply the kotlin-kapt plugin
    id 'kotlin-kapt'
}

android {
    // before AGP 4
    buildFeatures {
        dataBinding true
    }
}
```

Or

```groovy
    // on before AGP 4+
    dataBinding {
        enabled true
    }
```

2. Covert regular layout file to data binding layout Shortcut:
   right-click on the root element of the layout file, then choose
   "Convert to data binding layout"

3. Inflates root view in a data binding way.
```Kotlin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)
        binding.greeting = "Hello DataBinding"
    }
```


4. Changes the value of variables bound
```Kotlin
binding.greeting = "Hello DataBinding"
```

> Note: As view binding can coexist with data binding, so be careful enought to NOT mix a VB variable with an DB vaiable, otherwise you might not get the expected results. For example:
```Kotlin
binding.name = viewModel.name             // assigns to a DB String variable, named `name`, supporting two-way binding. -> UI will refresh accordingly
binding.plain_name.text = viewModel.name  // assigns to a VB TextView variable, named `plain_name`, supporting only one-way binding. -> UI won't refresh accordingly

```

5. Steps to implement ViewModel binding

- Adds dependencies to lifecycle View Model, if not yet
```groovy
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
```

- Creates custom ViewModel class
```Kotlin
class GreetingViewModel: ViewModel() {

    private val _greeting = MutableLiveData("Hello data binding")
    val greeting: LiveData<String> = _greeting

    fun changeGreeting(greetingMessage: String) {
        _greeting.value = greetingMessage
    }
}
```

- Declares ViewModel variable
```xml
    <data>
        <variable name="vm" type="vm.GreetingViewModel" />
    </data>
```

- Uses layout expression to change view attributes
```xml
    <TextView>
        android:text="@{vm.greeting}"
    </TextView>
```

- Inflates and binds ViewModel object
```Kotlin
    private val viewModel: GreetingViewModel by lazy {
        ViewModelProvider(this).get(GreetingViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)
        binding.vm = viewModel
    }
```

- Changes ViewModel value to refresh UI
```Kotlin
    override fun onResume() {
        super.onResume()
        viewModel.changeGreeting("Changed greeting: Haha")
    }
```
- Accepts user input (e.g. click event) and refresh UI elements accordingly
  - Defines responding function
      ```KOTLIN
          fun changeRandomGreeting() {
            val idx = Random.Default.nextInt(famousSayings.size)
            _greeting.value = famousSayings[idx]
        }
      ```
  - Binds the responding function to specific user event
    ```xml
        <TextView>
            android:onClick="@{() -> vm.changeRandomGreeting()}"
        </TextView>
    ```
  - **Adds lifecycle owner of the binding**
    ```kotlin
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)

        binding.vm = viewModel

        // Set up life cycle owner of the binding, otherwise, the UI
        // won't get refreshed as a response to user click event
        binding.lifecycleOwner = this
    }
    ```
    > Note: If failing to add binding lifecycle then the UI won't get refreshed.

# References
1. Android Doc. [Data Binding Libraray Overview](https://developer.android.com/topic/libraries/data-binding). 12/08/2020
2. Jose Alcérreca(Jalc). [Codelabs - Data Binding in Android](https://codelabs.developers.google.com/codelabs/android-databinding). 12/08/2020
3. Jose Alcérreca(Jalc). [Android Data Binding Library — from Observable Fields to LiveData in two steps](https://medium.com/androiddevelopers/android-data-binding-library-from-observable-fields-to-livedata-in-two-steps-690a384218f2). 12/08/2020

# Q & A
1. What's Data Binding Layout
   > The layout file with data binding enabled. It consist of two sections -  
   > the data section and view section, for layout variables and UI elements, respectively.

    ```xml
    <layout>
       <data>
         <!-- definition of data variables goes here -->
       </data>
       
       <ViewRoot>
         <!-- view declaration goes here --> 
       </ViewRoot>
    </layout>
    ```

2. What's Layout Variable
   > Variables defined in the layout file and used to bilaterally exchange  
   > info between the model and view.

3. What's Binding Expression
   > An regular Kotlin/Java expression that starts with a `@` sign and is  
   > wrapped inside curly braces {}. It operates on layout variables and is  
   > placed in the layout file as applicable element attribute values.

    Basic expression examples:
    ```xml
    <!-- Some examples of complex layout expressions -->
    <TextView>
        android:text="@{String.valueOf(index + 1)}"
        android:visibility="@{age < 13 ? View.GONE : View.VISIBLE}"
        android:transitionName='@{"image_" + id}'
    </TextView>
    ```

    ViewModel-bound expression examples:
    ```xml
    <TextView>
        android:text="@{viewmodel.name}"
        <!-- Bind the nameVisible property of the viewmodel to the visibility attribute -->
        android:visibility="@{viewmodel.nameVisible}"
        <!-- Call the onLike() method on the viewmodel when the View is clicked. -->
        android:onClick="@{() -> viewmodel.onLike()}"
    </TextView>
    ```

4. What's Binding Adapter
5. What's Binding Class


