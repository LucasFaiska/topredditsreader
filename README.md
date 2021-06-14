# Improvement points
- Koin made me import all modules on app module, need to find a way to better this relationship between modules
- Some modules imports the same libs and the code was been repeated, it's is a complication when needs to give manutention
- Night theme
- Create a Observable view modle to observe directly on XML variables like `isEmptyPosts` and dont need to observe them on the activity
- Find a way for adapter not to blink when update, I try it with `adapter.setHasStableIds(true)` but the reddit json has no id field to use on method `getId` in adapter and I couldn't think in a way to do that
