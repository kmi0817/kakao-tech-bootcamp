class Fighter extends Character{
    private volatile int hp;
    private int power;
    private String name;

    Fighter(int id, int hp, int power) {
        super(id);
        this.hp = hp;
        this.power = power;
    }

    int getHp() {
        return hp;
    }

    void setHp(int hp) {
        this.hp = hp;
    }

    int getPower() {
        return power;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    boolean isDead() {
        return hp <= 0;
    }

    void attack(Fighter target) {
        try {
            if (isDead()) {
                throw new RuntimeException(getName() + "은/는 죽었기 때문에 다른 캐릭터를 공격할 수 없습니다.");
            }

            if (target.isDead()) {
                throw new RuntimeException(target.getName() + "은/는 이미 죽은 캐릭터라 공격할 수 없습니다.");
            }

            target.setHp(target.getHp() - power);

            if (target.isDead()) {
                System.out.printf("%s이/가 %d만큼 공격하여 %s이/가 죽었습니다.\n", getName(), getPower(), target.getName());
            } else {
                System.out.printf("%s이/가 %d만큼 공격하여 %s의 hp가 %d로 감소했습니다.\n", getName(), getPower(), target.getName(), target.getHp());
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
