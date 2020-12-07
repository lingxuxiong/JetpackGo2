# View Binding

# What's View Binding

> *View binding is a feature that allows you to more easily write code
> that interacts with views. Once view binding is enabled in a module,
> it generates a binding class for each XML layout file present in that
> module. An instance of a binding class contains direct references to
> all views that have an ID in the corresponding layout.*
> https://developer.android.com/topic/libraries/view-binding#setup

# How to enable View Binding
```kotlin
// Android Studio 4.0
android {
    buildFeatures {
        viewBinding = true
    }
}
```

# Use binding view as view contents
`ViewBinding` will automatically generate an binding file that corresponds
to the layout source file. Assume `activity_main.xml` was created with a name
TextView whose id is `greeting`, the the code snippet to inflate and use
the binding view is as follows
```kotlin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.greeting.text = "Hello View Binding"

        setContentView(binding.root)
    }
```

# How to disable View Binding on a layout
```xml
<LinearLayout>
        ...
        tools:viewBindingIgnore="true" >
        ...
</LinearLayout>
```

# How to release binding view
In cases like fragments, as the host outlives the views, so references to
the views should be dereferenced appropriately.
```kotlin
private var _binding: ResultProfileBinding? = null
// This property is only valid between onCreateView and
// onDestroyView.
private val binding get() = _binding!!

override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    _binding = ResultProfileBinding.inflate(inflater, container, false)
    val view = binding.root
    return view
}

override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
}
```

View Binding is encouraged to be used as an replacement of `findViewById`,
in most cases, with Type-safe and Null-safe capability.

# References

1. Sean McQuillan.
   [Use view binding to replace findViewById](https://medium.com/androiddevelopers/use-view-binding-to-replace-findviewbyid-c83942471fc).02/14/2020,
   12/07/2020
2. Google Guide.
   [View Binding](https://developer.android.com/topic/libraries/view-binding).
   12/07/2020

