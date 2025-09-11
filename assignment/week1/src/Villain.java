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

            Hero hero = (Hero) target;

            if (hero.getHp() <= 0) {
                hero.decrementLife();

                if (hero.canRespawn()) {
                    hero.setHp(hero.getMaxHp() + hero.getHp());
                    System.out.printf("\t=> %s이/가 다시 살아났습니다 (목숨: %d, hp: %d).\n", hero.getName(), hero.getLives(), hero.getHp());
                } else {
                    System.out.println("\t=> " + hero.getName() + "은/는 다시 살아날 목숨이 없어 죽었습니다.");
                }
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
