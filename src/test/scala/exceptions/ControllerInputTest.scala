package cl.uchile.dcc.citric
package exceptions

import controller.GameController
import controller.states.MovingState
import model.panel.{HomePanel, NeutralPanel}
import model.unit.player.PlayerCharacter

import jdk.internal.vm.vector.VectorSupport.test
import org.junit.Assert.{assertEquals, assertThrows}

class ControllerInputTest extends munit.FunSuite {


  private var controller: GameController = _
  private var state: MovingState = _
  private var player: PlayerCharacter = _
  private var panel: NeutralPanel = _
  private var panel2: NeutralPanel = _
  private var panel3: HomePanel = _

  override def beforeEach(context: BeforeEach): Unit = {

    controller = new GameController
    state = new MovingState
    player = new PlayerCharacter("Martin Reds", 1, 1, 1, 1)
    panel = new NeutralPanel
    panel2 = new NeutralPanel
    panel3 = new HomePanel(player)

    panel3.removeCharacter(player)
    panel.addCharacter(player)

    panel.addPanel(panel2)
    panel.addPanel(panel3)

    state.controller = controller
    controller.selected = player
    controller.panel = panel

  }



  test("Invalid input test") {
    assertEquals(controller.panel, panel)
    assert(panel.characters.contains(player))
    assert(!panel2.characters.contains(player))
    assert(!panel3.characters.contains(player))
    interceptMessage[InvalidInputException]("An invalid input was found -- 50") {
      state.choosePath(50)
    }
  }


}
