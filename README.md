# Plugin API
The `pluginapi` package houses tsgrissom's Bukkit plugin API to help create fast, intelligent, and powerful plugins
for CraftBukkit servers.

## Package `pluginapi.chat`
Use `ClickTextBuilder` and `HoverTextBuilder` to quickly generate Chat Component API `TextComponents`. Follows Java's builder
pattern.

Use `FormattedListBuilder` classes to generate any of the following lists:
* Comma-delimited lists
* Multi-line lists
* Plain text lists
* Lists supporting fancy text (hover or click)

Use `TextBoxBuilder` to display data points within text boxes in the user's chat.

## Package `pluginapi.command`

Combine `CommandBase` and `CommandContext` to replace `CommandExecutor` and its `onCommand` method.

`CommandContext` encapsulates the parameters of the `onCommand` method and provide many new functions to make designing
commands much easier and more intuitive.

#### Sub-Package `flag`

`CommandFlagParser` can parse `CommandContext` arguments against specified `ValidCommandFlag`, enabling you to easily
identify command flags passed within the current context, unknown flags, and more.

`ValidCommandFlag` simply enumerates what is considered a valid flag for a command. Construct and pass to your
`CommandFlagParser` to determine if your valid flags were passed by the user at command-time.

#### Sub-Package `help`

Contains 

## Package `pluginapi.conversation`

An experimental package to interface with the Bukkit Conversation API. Mostly unused at the moment.

## Package `pluginapi.data`

Contains Kotlin data classes.

`QuotedStringSearchResult` is the object resulting from checking for quoted Strings within a `CommandContext`.

## Package `pluginapi.extension`

Contains many extension functions for both Kotlin and the Bukkit API.

#### Sub-Package `bukkit`

#### Sub-Package `kt`

## Package `pluginapi.functional`

Contains custom classes for use with the functional programming paradigm of Kotlin/JVM.

## Package `pluginapi.utility`

Contains utility classes which hold specialized functions that do not fall into other packages.

`EntityUtility` contains functions for categorizing and representing entity and their types.

`TimeUtility` contains functions for checking if inputs are valid times, converting between 12hr and 24hr clocks, etc.