class Villain extends Fighter {
    private final int attackLimit;
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

    @Override
    void attack(Fighter target) {
        try {
            if (attackCount >= attackLimit) {
                throw new RuntimeException(getName() + "은/는 공격 가능 횟수를 모두 소진해 더 이상 공격할 수 없습니다.");
            }

            super.attack(target);
            attackCount++;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return getName() + "\t: hp(" + getHp() + "),\tpower(" + getPower() +"),\tattack(" + getAttackCount() + "/" + getAttackLimit() + ")";
    }
}
