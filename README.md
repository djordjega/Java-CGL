# Conway's Game of Life - cellular automaton - Java
>>You can't get much simpler than Conway's Game of Life. 
>
>There are only four rules:
>
>- Any live cell with fewer than two live neighbours dies (as if by needs caused by underpopulation).
>- Any live cell with more than three live neighbours dies (as if by overcrowding).
>- Any live cell with two or three live neighbours lives (unchanged, to the next generation).
>- Any dead cell with exactly three live neighbours becomes a live cell.

---
### Some pseudo:
```
10 start year
    if year == 0 GOTO 20 
    elseif year == 100 GOTO 50 
    else GOTO 30
20 randomly generate x number of entities/cells
30 populate world 
    go trough the statuses and draw
    year++
40 check cell status
    go trough the rules for every cell
    GOTO 10
50 end
```
---

### Flowchart

https://drive.google.com/open?id=1H7rs2m75rARB9vTDlubwng_WPfVodCpw

---

### Domain models and vars:
- Cell: every cell on map: has status = false (dead) or true (alive)
- World / Map / Array of life: array of cells
- Year: fom 0 to >0
- Population, births, deaths ...
---

* try to implement real world data and plot it on world map

---