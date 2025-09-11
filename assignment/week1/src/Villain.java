class Villain extends Fighter {
    private int attackLimit;
    private int attackCount;

    Villain(int id, int hp, int power, int attackLimit) {
        super(id, hp, power);
        setName("쫄병" + id);
        this.attackLimit = attackLimit;
    }

    int getAttackLimit() {
        return attackLimit;
    }

    int getAttackCount() {
        return attackCount;
    }

    void incrementAttackCount() {
        attackCount++;
    }

    @Override
    public String toString() {
        return getName() + "\t: hp(" + getHp() + "),\tpower(" + getPower() +"),\tattack(" + getAttackCount() + "/" + getAttackLimit() + ")";
    }
}
