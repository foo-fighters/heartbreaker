package brickingbad.domain.game.alien;

public class DrunkAlienStrategy extends AlienStrategy {

    private CooperativeAlienStrategy cas;
    private ProtectingAlienStrategy pas;
    private RepairingAlienStrategy ras;

    public DrunkAlienStrategy(Alien alien) {
        cas = new CooperativeAlienStrategy(alien);
        pas = new ProtectingAlienStrategy();
        ras = new RepairingAlienStrategy();
    }

    @Override
    void performAction() {

    }

}
