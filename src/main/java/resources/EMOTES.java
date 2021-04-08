package resources;

public enum EMOTES {
    RDG(":RubberDuckyGreen:820700438084845568"),
    RDR(":RubberDuckyRed:820700478467604502"),
    NLD(":NotLikeDis:825375445624946719"),
    // Progressbar left
    L0(":left0:829444101308547136"),
    L1(":left1:829444101551423508"),
    L2(":left2:829444101614600252"),
    L3(":left3:829444101619318814"),
    L4(":left4:829444101627707452"),
    L5(":left5:829444101639372910"),
    L6(":left6:829444304799399946"),
    L7(":left7:829444328626847745"),
    L8(":left8:829444338840633387"),
    L9(":left9:829444353637875772"),
    L10(":left10:829444368329998387"),
    // Progressbar middle
    M0(":middle0:829444383614959686"),
    M1(":middle1:829444395388108850"),
    M2(":middle2:829444414194581574"),
    M3(":middle3:829444414203625472"),
    M4(":middle4:829444414312546365"),
    M5(":middle5:829444445618831440"),
    M6(":middle6:829444468192837652"),
    M7(":middle7:829444594801573928"),
    M8(":middle8:829444643938107452"),
    M9(":middle9:829444666230308864"),
    M10(":middle10:829444683821482004"),
    // Progressbar right
    R0(":right0:829444702105239613"),
    R1(":right1:829444715803443261"),
    R2(":right2:829444741062066246"),
    R3(":right3:829444752251551744"),
    R4(":right4:829444776746549260"),
    R5(":right5:829444791137206332"),
    R6(":right6:829444802928050206"),
    R7(":right7:829444814180319242"),
    R8(":right8:829444826843578378"),
    R9(":right9:829444840520810586"),
    R10(":right10:829444852583759913");

    private String id;

    private EMOTES(String id) {
        this.id = id;
    }

    public String getAsReaction() {
        return this.id;
    }
}
