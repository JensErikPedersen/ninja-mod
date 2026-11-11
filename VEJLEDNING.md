# Ninja Mod (Minecraft 26.2, Fabric)

## Hvad er der i mod'en

| Ting | Opskrift (arbejdsbord) | Hvad den gør |
|---|---|---|
| **Ninjahætte, -jakke, -bukser, -støvler** | Som jernrustning, men af **sort uld** med **1 jernbarre** i midten (støvler: uld øverst, 2 jernbarrer nederst) | Sort rustning. Holdbarhed som ringbrynje, beskyttelse 13 (ringbrynje 11, jern 15). 2-3 dele giver **Speed I**, alle 4 giver **Speed II**. Repareres med sort uld. |
| **Springstav** | 2 pinde på skrå + 1 fjer øverst til højre (som et spyd) | Højreklik: spring ca. 7 blokke op og 14 frem. Kan ikke slå. Ingen faldskade ved landing. Holdes som et spyd. |
| **Kastestjerne** (giver 4) | Jernbarre i midten, 4 jernklumper (nuggets) rundt om | Højreklik for at kaste. Flyver og skader som en pil fra en fuldt spændt bue. 7 sekunders cooldown. Kan samles op igen. |
| **Mini-katana** | Jernbarre øverst til højre, pind nederst til venstre | Samme skade (6) og rækkevidde som et jernsværd, angrebshastighed 2. Lidt mindre i hånden. |

Opskrifterne dukker op i opskriftsbogen, når du har samlet ingredienserne op (fx sort uld + jernbarre for rustningen).
I kreativ tilstand ligger det hele under fanen *Kamp*.

## Byg og spil

```
.\gradlew build
```

Kopiér `build\libs\ninjamod-1.0.0.jar` til `C:\Users\jense\AppData\Roaming\.minecraft\mods` og start profilen **fabric-loader-26.2**.
Eller test direkte fra IntelliJ med **Tasks → fabric → runClient**.

## Ting du kan skrue på

| Fil | Tal |
|---|---|
| `JumpStaffItem.java` | `JUMP_UP` (højde) og `JUMP_FORWARD` (længde) |
| `ShurikenItem.java` | `COOLDOWN_TICKS` (20 ticks = 1 sekund) |
| `ModItems.java` | Rustningens beskyttelse og holdbarhed, katanaens skade og hastighed |
| `NinjaMod.java` | Speed-effekten fra rustningen |
