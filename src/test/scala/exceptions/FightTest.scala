package cl.uchile.dcc.citric
package exceptions
import model.unit.wild.Chicken
import model.unit.wild.Seagull
import model.panel.EncounterPanel


class FightTest extends munit.FunSuite {
  private val panel: EncounterPanel = new EncounterPanel
  private val chicken: Chicken = new Chicken(panel)
  private val seagull: Seagull = new Seagull(panel)

  test("A wild unit should not be attacked by another") {
    interceptMessage[InvalidFightException]("Two Wild Units should not be able to fight each other!")
      {chicken.attackWild(seagull, 3)}
  }

}