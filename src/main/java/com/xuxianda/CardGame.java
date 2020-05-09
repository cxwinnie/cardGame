package com.xuxianda;

import com.microsoft.z3.*;

import java.util.*;

/**
 * Created by XiandaXu on 2020/4/3.
 */
public class CardGame {
    static int index = 1; //当前操作的玩家
    static Integer X = 4;//类型
    static Integer Y = 7;//编号
    static List<Card> initList = new ArrayList(); //初始化28张卡牌，分发完成之后剩下1张卡牌
    static List<Card> play1 = new ArrayList(); //玩家1
    static List<Card> play2 = new ArrayList(); //玩家2
    static List<Card> play3 = new ArrayList(); //玩家3
    static List<Card> putCards = new ArrayList<>();
    static List<List<Card>> hiddenListCard = new ArrayList<>(); //暗置的卡牌数组
    static Scanner scanner = new Scanner(System.in);
    static boolean flag = false; //游戏结束标志

    static {
        for (int i = 1; i <= X; i++) {
            for (int j = 1; j <= Y; j++) {
                initList.add(new Card(i, j));
            }
        }

    }


    /**
     * 发牌功能
     */
    public static void deliverCard() {
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        Random random = new Random();
        while (true) {
            int i = random.nextInt(3);
            int j = random.nextInt(initList.size());
            switch (i) {
                case 0:
                    if (count1 < 9) {
                        play1.add(initList.get(j));
                        count1++;
                        initList.remove(j);
                    }
                    break;
                case 1:
                    if (count2 < 9) {
                        play2.add(initList.get(j));
                        count2++;
                        initList.remove(j);
                    }
                    break;
                case 2:
                    if (count3 < 9) {
                        play3.add(initList.get(j));
                        count3++;
                        initList.remove(j);
                    }
                    break;
            }
            if (count1 + count2 + count3 == 27) {
                break;
            }
        }
        sortList(play1);
        sortList(play2);
        sortList(play3);
    }

    /**
     * 对卡牌进行排序
     *
     * @param list
     * @return
     */
    public static void sortList(List<Card> list) {
        Collections.sort(list, new Comparator<Card>() {
            public int compare(Card o1, Card o2) {
                if (o1.getType() == o2.getType()) {
                    return o1.getNo() - o2.getNo();
                } else {
                    return o1.getType() - o2.getType();
                }
            }
        });
    }

    /**
     * 从当前玩家的卡牌中找出对应的相同类型和编号的卡牌
     *
     * @param play
     * @param type
     * @param no
     * @return
     */
    /*public static Card getCardFromList(List<Card> play, Integer type, Integer no) {
        for (Card card : play) {
            if (card.getType().equals(type) && card.getNo().equals(no)) {
                return card;
            }
        }
        return null;
    }*/

    /**
     * 查看玩家手中是否有3张相同花色连续序号的卡牌
     *
     * @param play
     */
    public static List<List<Card>> sameNoOrTripleNo(List<Card> play, Integer type) {
        List<List<Card>> list = new ArrayList();
        //3张相同花色连续序号的卡牌
        int[] numArray = new int[8];
        play.stream().forEach(x -> {
            /**
             * 将所有此类型的卡牌的编号放入数组中
             */
            if (x.getType().equals(type)) {
                numArray[x.getNo()] = 1;
            }
        });
        for (int no = 1; no <= 5; no++) {
            if (numArray[no] == 1 && numArray[no + 1] == 1 && numArray[no + 2] == 1) {
                for (int j = 0; j < play.size() - 2; j++) {
                    Card card = play.get(j);
                    if (card.getType().equals(type) && card.getNo().equals(no)) {
                        List<Card> tripleNo = new ArrayList<>();
                        tripleNo.add(play.get(j));
                        tripleNo.add(play.get(j + 1));
                        tripleNo.add(play.get(j + 2));
                        list.add(tripleNo);
                        break;
                    }
                }
            }
        }
        //3张不同花色但是相同序号
        /*for (Card card : play) {
            if (card.getType().equals(type)) {
                int[] typeArray = new int[5];
                Integer no = card.getNo();
                for (Card tmp : play) {
                    if (tmp.getNo().equals(no)) {
                        typeArray[tmp.getType()] = 1;
                    }
                }
                if (typeArray[1] + typeArray[2] + typeArray[3] + typeArray[4] == 3) {
                    List<Card> tripleNo = new ArrayList<>();
                    for (int i = 1; i <= 4; i++) {
                        if (typeArray[i] == 1) {
                            tripleNo.add(getCardFromList(play, i, no));
                        }
                    }
                    list.add(tripleNo);
                } else if (typeArray[1] + typeArray[2] + typeArray[3] + typeArray[4] == 4) {
                    for (int i = 1; i <= 4; i++) {
                        if (i != type) {
                            for (int j = i + 1; j <= 4; j++) {
                                if (j != type) {
                                    List<Card> tripleNo = new ArrayList<>();
                                    tripleNo.add(getCardFromList(play, type, no));
                                    tripleNo.add(getCardFromList(play, i, no));
                                    tripleNo.add(getCardFromList(play, j, no));
                                    list.add(tripleNo);
                                }
                            }
                        }
                    }
                }
            }
        }*/
        return list;
    }


    public static List<Card> getSingleCard(List<Card> play, Integer type) {
        List<Card> putCard = new ArrayList<>();
        play.forEach(x -> {
            if (x.getType().equals(type)) {
                putCard.add(x);
            }
        });
        return putCard;
    }

    public static void putTripleCard(List<Card> play, Integer index, Integer guessX) {
        List<List<Card>> sameNoOrTripleNo = sameNoOrTripleNo(play, guessX);
        if (sameNoOrTripleNo.size() > 0) {
            System.out.println("玩家" + index + "符合条件的卡牌如下\n" + sameNoOrTripleNo);
            System.out.println("玩家" + index + "输入需要出第几组卡牌:");
            Integer cardListIndex = scanner.nextInt();
            System.out.println("请输入需要暗置的卡牌");
            Integer hiddenIndex1 = scanner.nextInt(); //第一张暗置卡牌
            Integer hiddenIndex2 = scanner.nextInt(); //第二张暗置卡牌
            List<Card> cards = sameNoOrTripleNo.get(cardListIndex - 1);
            cards.get(hiddenIndex1 - 1).setHidden(true);
            cards.get(hiddenIndex2 - 1).setHidden(true);
            cards.stream().forEach(x -> {
                play.remove(x);
            });
            hiddenListCard.add(cards);
            moveHiddenToPut();
        } else {
            List<Card> singleCard = getSingleCard(play, guessX);
            System.out.println("玩家" + index + "符合条件的卡牌如下\n" + singleCard);
            if (singleCard.size() > 0) {
                System.out.println("请输入需要出牌的编号:");
                Integer singleIndex = scanner.nextInt();
                putCards.add(singleCard.get(singleIndex - 1));
                play.remove(singleCard.get(singleIndex - 1));
            } else {
                System.out.println("玩家" + index + "无卡牌可出");
            }
        }
    }

    /**
     * 查阅隐藏卡牌列表，如果有编号为1或者7的隐藏卡牌（可以推断出为1、2、3或者5、6、7）
     * 或者已知两张卡牌编号差为为2，那么剩余一张卡牌也是已知的
     */
    public static void moveHiddenToPut() {
        Iterator<List<Card>> iterator = hiddenListCard.iterator();
        while (iterator.hasNext()) {
            List<Card> tripleCard = iterator.next();
            List<Integer> listNo = new ArrayList();  //存储所有已知的编号
            for (Card card : tripleCard) {
                if (card.isHidden() == false && (card.getNo() == 1 || card.getNo() == 7)) {
                    putCards.addAll(tripleCard);
                    listNo.clear();
                    iterator.remove();
                    break;
                }
                if (card.isHidden() == false) {
                    listNo.add(card.getNo());
                }
            }
            if (listNo.size() == 3) {
                putCards.addAll(tripleCard);
                iterator.remove();
            } else if (listNo.size() == 2) {
                Integer max = listNo.get(0);
                Integer min = listNo.get(1);
                if(max < min){
                    Integer temp = max;
                    max = min;
                    min = temp;
                }
                if (max - min == 2) {
                    putCards.addAll(tripleCard);
                    iterator.remove();
                }
            }
        }
        putCards.stream().forEach(x -> {
            x.setHidden(false);
        });
    }

    /**
     * z3解析器进行解析
     */
    public static void z3Solver(List<Card> player) {
        Log.open("test.log");
        HashMap<String, String> cfg = new HashMap<String, String>();
        cfg.put("model", "true");
        Context ctx = new Context(cfg);

        List<IntExpr> allKnowCards = new ArrayList<IntExpr>();  //所有的已知卡牌
        for (Card card : putCards) {
            IntNum intNum = ctx.mkInt((card.getType() - 1) * 7 + card.getNo());
            allKnowCards.add(intNum);  //将已经打出的卡牌放入
        }
        for (Card card : player) {
            IntNum intNum = ctx.mkInt((card.getType() - 1) * 7 + card.getNo());
            allKnowCards.add(intNum);  //将玩家的卡牌放入
        }

        List<BoolExpr> condition = new ArrayList<>();       //所有的条件

        IntExpr killCard = ctx.mkIntConst("killCard");   //杀手牌的z3表示
        allKnowCards.add(killCard);
        BoolExpr killCardBoolExpr = ctx.mkAnd(ctx.mkLe(ctx.mkInt(1), killCard),   //大于等于1
                ctx.mkLe(killCard, ctx.mkInt(28)));                               //小于等于28
        condition.add(killCardBoolExpr);

        IntExpr[] revert = new IntExpr[allKnowCards.size()];
        BoolExpr initialCondition = ctx.mkDistinct(allKnowCards.toArray(revert));
        condition.add(initialCondition);

        BoolExpr allResult = ctx.mkTrue();

        for (List<Card> tripleCard : hiddenListCard) {
            List<List<Card>> allPossibleCard = getPossibleCard(tripleCard);
            List<BoolExpr> orCondition = new ArrayList<>();
            for (List<Card> cards : allPossibleCard) {
                revert = new IntExpr[allKnowCards.size() + 3];
                allKnowCards.toArray(revert);
                revert[allKnowCards.size()] = ctx.mkInt((cards.get(0).getType() - 1) * 7 + cards.get(0).getNo());
                revert[allKnowCards.size() + 1] = ctx.mkInt((cards.get(1).getType() - 1) * 7 + cards.get(1).getNo());
                revert[allKnowCards.size() + 2] = ctx.mkInt((cards.get(2).getType() - 1) * 7 + cards.get(2).getNo());
                BoolExpr trupleExpr = ctx.mkDistinct(revert);
                orCondition.add(trupleExpr);
            }
            BoolExpr orResult = orCondition.get(0);
            for (int i = 1; i < orCondition.size(); i++) {
                orResult = ctx.mkOr(orResult, orCondition.get(i));
            }
            condition.add(orResult);
        }

        for (BoolExpr tmp : condition) {
            allResult = ctx.mkAnd(tmp, allResult);
        }

        Solver s = ctx.mkSolver();
        s.add(allResult);
        if (s.check() == Status.SATISFIABLE) {
            Model m = s.getModel();
            Expr evaluate = m.evaluate(killCard, false);
            Integer num = new Integer( evaluate.toString());
            System.out.println("----------------------------------------------");
            System.out.println("玩家"+index+":   z3解析器分析的结果" + "<"+((num-1)/7+1)+","+((num-1)%7+1)+">");
            System.out.println("----------------------------------------------");
        }
    }

    public static List<List<Card>> getPossibleCard(List<Card> cards) {
        List<List<Card>> allPossibleCard = new ArrayList<>();
        List<Card> hiddenCard = new ArrayList<Card>();
        List<Card> explicitCard = new ArrayList<Card>();
        for (Card card : cards) {
            if (card.isHidden()) {
                hiddenCard.add(card);
            } else {
                explicitCard.add(card);
            }
        }
        if (explicitCard.size() == 2) {   //编号只可能是连续的
            Integer max = explicitCard.get(0).getNo();
            Integer min = explicitCard.get(1).getNo();
            if (max < min) {
                Integer tmp = max;
                max = min;
                min = tmp;
            }
            List<Card> list1 = new ArrayList<>();
            Card card1 = new Card(cards.get(0).getType(), min - 1);
            list1.add(card1);
            list1.addAll(explicitCard);
            List<Card> list2 = new ArrayList<>();
            Card card2 = new Card(cards.get(0).getType(), max + 1);
            list2.add(card2);
            list2.addAll(explicitCard);
            allPossibleCard.add(list1);
            allPossibleCard.add(list2);
        } else { //显示的卡只有一张
            Card card = explicitCard.get(0);
            if (card.getNo() == 6) {  //只有两种可能性
                List<Card> list1 = new ArrayList<>();
                Card card1 = new Card(card.getType(), 5);
                Card card2 = new Card(card.getType(), 7);
                list1.add(card1);
                list1.add(card2);
                list1.add(card);
                List<Card> list2 = new ArrayList<>();
                Card card3 = new Card(card.getType(), 4);
                Card card4 = new Card(card.getType(), 5);
                list2.add(card3);
                list2.add(card4);
                list2.add(card);
                allPossibleCard.add(list1);
                allPossibleCard.add(list2);
            } else if (card.getNo() == 2) {//只有两种可能性
                List<Card> list1 = new ArrayList<>();
                Card card1 = new Card(card.getType(), 1);
                Card card2 = new Card(card.getType(), 3);
                list1.add(card1);
                list1.add(card2);
                list1.add(card);
                List<Card> list2 = new ArrayList<>();
                Card card3 = new Card(card.getType(), 3);
                Card card4 = new Card(card.getType(), 4);
                list2.add(card3);
                list2.add(card4);
                list2.add(card);
                allPossibleCard.add(list1);
                allPossibleCard.add(list2);
            } else { //三种可能性
                List<Card> list1 = new ArrayList<>();
                Card card1 = new Card(card.getType(), card.getNo() - 1);
                Card card2 = new Card(card.getType(), card.getNo() - 2);
                list1.add(card1);
                list1.add(card2);
                list1.add(card);
                List<Card> list2 = new ArrayList<>();
                Card card3 = new Card(card.getType(), card.getNo() - 1);
                Card card4 = new Card(card.getType(), card.getNo() + 1);
                list2.add(card3);
                list2.add(card4);
                list2.add(card);
                List<Card> list3 = new ArrayList<>();
                Card card5 = new Card(card.getType(), card.getNo() + 2);
                Card card6 = new Card(card.getType(), card.getNo() + 1);
                list3.add(card5);
                list3.add(card6);
                list3.add(card);

                allPossibleCard.add(list1);
                allPossibleCard.add(list2);
                allPossibleCard.add(list3);
            }
        }
        return allPossibleCard;
    }

    /**
     * 每个玩家开始轮流进行卡牌操作
     */
    public static void circlePlay() {
        Integer guessX; //猜牌的类型
        Integer guessY; //猜牌的编号
        while (true) {
            if (index == 1) {
                z3Solver(play1);
            } else if (index == 2) {
                z3Solver(play2);
            } else {
                z3Solver(play3);
            }
            System.out.println("请第" + index + "玩家输入需要操作编号（1：猜牌，2：请求卡牌，3：查阅卡牌）");
            int operation = scanner.nextInt();
            switch (operation) {
                case 1:
                    /**
                     * 玩家说出一张牌, 如果它是杀手牌, 则游戏结束
                     */
                    System.out.println("请输入类型和编号:");
                    guessX = scanner.nextInt();
                    guessY = scanner.nextInt();
                    Card card = initList.get(0);
                    if (card.getType().equals(guessX) && card.getNo().equals(guessY)) {
                        flag = true;
                    }
                    break;
                case 2:
                    /**
                     *  玩家向其它玩家请求卡牌和放置卡牌, 具
                     体来讲分为以下 3 步:
                     1) A1.1 玩家向其他两位玩家请求某个类型的牌.
                     2) A1.2 其他两位玩家如果手中有这种类型的牌,
                     则选取一张牌面向下交给当前玩家; 如果手中没有这
                     种类型的牌, 则告诉所有玩家.
                     3) A1.3 当前玩家根据手中的牌, 如果出现3 张相同花色并且连续序
                     号的手牌时, 需要将其中两张暗置, 只暴露其中一张来
                     进行出牌; 若手中没有符合上述情形的三张手牌时, 只
                     需出手牌中的任意一张, 无需暗置
                     */
                    System.out.println("请输入卡牌类型:");
                    guessX = scanner.nextInt();  //玩家请求一张卡牌
                    if (index == 1) {
                        putTripleCard(play2, 2, guessX);
                        putTripleCard(play3, 3, guessX);
                    } else if (index == 2) {
                        putTripleCard(play1, 1, guessX);
                        putTripleCard(play3, 3, guessX);
                    } else {
                        putTripleCard(play1, 1, guessX);
                        putTripleCard(play2, 2, guessX);
                    }
                    break;
                case 3:
                    System.out.println(hiddenListCard + "\n请输入想查阅的卡牌序号");
                    Integer hiddenIndex = scanner.nextInt();
                    int count = 0;
                    for (int i = 0; i < hiddenListCard.size() && count < hiddenIndex; i++) {
                        List<Card> cards = hiddenListCard.get(i);
                        for (Card tmp : cards) {
                            if (tmp.isHidden()) {
                                if (count == hiddenIndex - 1) {
                                    count++;
                                    tmp.setHidden(false);
                                    break;
                                }
                                count++;
                            }

                        }
                    }
                    System.out.println("查阅的卡牌序号结果\n" + hiddenListCard);
                    moveHiddenToPut();
            }
            if (flag) {
                System.out.println("游戏结束");
                break;
            }
            index = index % 3 + 1;
            System.out.println("\n\n\n");
        }
    }

    public static void main(String[] args) {
        deliverCard();
        circlePlay();
    }

}
