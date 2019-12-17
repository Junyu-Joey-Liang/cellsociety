# Code Review Lab

### Refactoring focus:
**1. shorten long methods**
    There were many long methods in the code hurts the readability of the code. We made refactored the code so that each logical portion of the method is wrapped by its own method. This makes the code more readable, and easier for future changes.
**2. reduce duplicate code**
    There were many instances of similar code because many of our simulations had to be set up similarly, but with their own twist to it. To fix this issue, the xml parsing code was refactored and an abstract class was created to handle and perform all the similar initialization tasks for the simulation. To handle each simulation's uniqueness, subclasses were created for each simulation. This significantly reduced the amount of code that was duplicated.  
**3. remove magic numbers**
    There were magic numbers in the code, especially the visualization parameters. This makes it hard for modifications and also is inefficient since many parameters are shared by different classes. We moved the shared parameters into resource files, and used final static variables to maintain constants in a class.
**5. reduce redundant elements**
**6. Throwing errors**
**7. Too many boolean statements in one line**
    There were some boolean methods that had too many boolean statements in one line(4~5). These were refactored by dividing them to two or three different parts, assigning each to a boolean variable or a boolean method, and combining them later.
**8. Mutable object getters/setters**
    There were getters/setters, especially in the simulations part, that returned or used mutable objects (list, map) directly. We tried solving this problem by copying the object to a new mutable object, and then using the copied one.


___
### Refactoring Commits:
98fe86719cd3c3d720f9bc19174132dda22c376c - "optimized imports"

a6778eadde01cbf0b0b9fab109bfdb33180adaf3 - "refactoring"

627a9f39f64668047025393481d6a52db87d1d9c - 
"Merge remote-tracking branch 'origin/master'

49d3a0c167ac45629eb7890ac95ccda8530efdd9 - "refactoring process"

98a31f20cd92a069cd6cc50182c0cd725733afe6 - "Merge brach 'eh174' into master"

83cfd5fa6a94068b695dca25cd0e4c14a207af01 - "fixed magic numbers in visualization"

d2d4b6eefb0bc6d8d3fd8c85f8f365b23f1bf48a - "refactored code to remove magic number, fixed a bug in triangle view (with the calculations)"

bdb4dece63d742a0b9aa15eca6d447eb5758b33d - "Merge branch 'jl751' into 'master'"

6a98bce8601b7e33c3f99fdcccdd27824f6ed933 - "More refactoring done, if statements in more than one line"

6398e2d0405a2ca7af11ede14a78c54c09b2c38b - "Merge branch 'eh174' into 'master'"

4eb315d18430d27a77d7824c77aaaf6440032025 - "IMPLEMENTED ALL SUBCLASSES FOR XML"

a3c36c720120f89f6aa5b813a5e73e826fc7813c - "ALL XML SUBCLASSES ADDED"

18c2718556b2bcef97f694a631b6a7a52fdf5cf7 - "fixed conflicts"



