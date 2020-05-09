package com.xuxianda;

import com.microsoft.z3.*;
import org.junit.Test;

import java.util.*;

/**
 * Created by XiandaXu on 2020/4/3.
 */
public class Test1 {

    public static void main(String[] args) {
        List list = new ArrayList();
        list.add(new Card(1, 1));
        System.out.println(list);
    }

    @Test
    public void test1() {
        Random random = new Random();
        while (true) {
            int i = random.nextInt(3);
            System.out.println(i);
        }
    }

    @Test
    public void test2() {
        int[] numArray = new int[8];
        System.out.println(numArray);
    }

    @Test
    public void sudokuExample() {
        Log.open("test.log");
        HashMap<String, String> cfg = new HashMap<String, String>();
        cfg.put("model", "true");
        Context ctx = new Context(cfg);
        // 9x9 matrix of integer variables
        IntExpr[][] X = new IntExpr[9][];
        for (int i = 0; i < 9; i++) {
            X[i] = new IntExpr[9];
            for (int j = 0; j < 9; j++)
                X[i][j] = (IntExpr) ctx.mkConst(
                        ctx.mkSymbol("x_" + (i + 1) + "_" + (j + 1)),
                        ctx.getIntSort());
        }

        // each cell contains a value in {1, ..., 9}
        BoolExpr[][] cells_c = new BoolExpr[9][];
        for (int i = 0; i < 9; i++) {
            cells_c[i] = new BoolExpr[9];
            for (int j = 0; j < 9; j++)
                cells_c[i][j] = ctx.mkAnd(ctx.mkLe(ctx.mkInt(1), X[i][j]),
                        ctx.mkLe(X[i][j], ctx.mkInt(9)));
        }

        // each row contains a digit at most once
        BoolExpr[] rows_c = new BoolExpr[9];
        for (int i = 0; i < 9; i++)
            rows_c[i] = ctx.mkDistinct(X[i]);

        // each column contains a digit at most once
        BoolExpr[] cols_c = new BoolExpr[9];
        for (int j = 0; j < 9; j++) {
            IntExpr[] col = new IntExpr[9];
            for (int i = 0; i < 9; i++) {
                col[i] = X[i][j];
            }
            cols_c[j] = ctx.mkDistinct(col);
        }

        // each 3x3 square contains a digit at most once
        BoolExpr[][] sq_c = new BoolExpr[3][];
        for (int i0 = 0; i0 < 3; i0++) {
            sq_c[i0] = new BoolExpr[3];
            for (int j0 = 0; j0 < 3; j0++) {
                IntExpr[] square = new IntExpr[9];
                for (int i = 0; i < 3; i++)
                    for (int j = 0; j < 3; j++)
                        square[3 * i + j] = X[3 * i0 + i][3 * j0 + j];
                sq_c[i0][j0] = ctx.mkDistinct(square);
            }
        }

        BoolExpr sudoku_c = ctx.mkTrue();
        for (BoolExpr[] t : cells_c)
            sudoku_c = ctx.mkAnd(ctx.mkAnd(t), sudoku_c);
        sudoku_c = ctx.mkAnd(ctx.mkAnd(rows_c), sudoku_c);
        sudoku_c = ctx.mkAnd(ctx.mkAnd(cols_c), sudoku_c);
        for (BoolExpr[] t : sq_c)
            sudoku_c = ctx.mkAnd(ctx.mkAnd(t), sudoku_c);

        // sudoku instance, we use '0' for empty cells
        int[][] instance = {{0, 0, 0, 0, 9, 4, 0, 3, 0},
                {0, 0, 0, 5, 1, 0, 0, 0, 7}, {0, 8, 9, 0, 0, 0, 0, 4, 0},
                {0, 0, 0, 0, 0, 0, 2, 0, 8}, {0, 6, 0, 2, 0, 1, 0, 5, 0},
                {1, 0, 2, 0, 0, 0, 0, 0, 0}, {0, 7, 0, 0, 0, 0, 5, 2, 0},
                {9, 0, 0, 0, 6, 5, 0, 0, 0}, {0, 4, 0, 9, 7, 0, 0, 0, 0}};

        BoolExpr instance_c = ctx.mkTrue();
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (0 != instance[i][j])
                    instance_c = ctx.mkAnd(
                            instance_c,
                            ctx.mkEq(X[i][j], ctx.mkInt(instance[i][j])));

        Solver s = ctx.mkSolver();
        s.add(sudoku_c);
        s.add(instance_c);

        if (s.check() == Status.SATISFIABLE) {
            Model m = s.getModel();
            Expr[][] R = new Expr[9][9];
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++)
                    R[i][j] = m.evaluate(X[i][j], false);
            System.out.println("Sudoku solution:");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++)
                    System.out.print(" " + R[i][j]);
                System.out.println();
            }
        }
    }

    @Test
    public void findModelExample2() {

        Log.open("test.log");
        HashMap<String, String> cfg = new HashMap<String, String>();
        cfg.put("model", "true");
        Context ctx = new Context(cfg);

        System.out.println("FindModelExample2");
        Log.append("FindModelExample2");

        IntExpr x = ctx.mkIntConst("x");
        IntExpr y = ctx.mkIntConst("y");
        IntExpr one = ctx.mkInt(1);
        IntExpr two = ctx.mkInt(2);

        ArithExpr y_plus_one = ctx.mkAdd(y, one);

        BoolExpr c1 = ctx.mkLt(x, y_plus_one);
        BoolExpr c2 = ctx.mkGt(x, two);

        BoolExpr q = ctx.mkAnd(c1, c2);

        System.out.println("model for: x < y + 1, x > 2");
        Model model = check(ctx, q, Status.SATISFIABLE);
        System.out.println("x = " + model.evaluate(x, false) + ", y ="
                + model.evaluate(y, false));

        /* assert not(x = y) */
        BoolExpr x_eq_y = ctx.mkEq(x, y);
        BoolExpr c3 = ctx.mkNot(x_eq_y);

        q = ctx.mkAnd(q, c3);

        System.out.println("model for: x < y + 1, x > 2, not(x = y)");
        model = check(ctx, q, Status.SATISFIABLE);
        System.out.println("x = " + model.evaluate(x, false) + ", y = "
                + model.evaluate(y, false));
    }

    Model check(Context ctx, BoolExpr f, Status sat) {
        Solver s = ctx.mkSolver();
        s.add(f);
        if (s.check() != sat)
            System.out.println("12345");
        if (sat == Status.SATISFIABLE)
            return s.getModel();
        else
            return null;
    }

    @Test
    public void test3() {
        Log.open("test.log");
        HashMap<String, String> cfg = new HashMap<String, String>();
        cfg.put("model", "true");
        Context ctx = new Context(cfg);

        List<IntExpr> allKnowCards = new ArrayList<IntExpr>();  //所有的已知卡牌
        List<Card> allUnknowCards = new ArrayList<>();    //所有的未知卡牌(2,3)
        allUnknowCards.add(new Card(2,3));
        List<BoolExpr> condition = new ArrayList<>();       //所有的条件
        IntExpr killCard = ctx.mkIntConst("killCard");
        allKnowCards.add(killCard);
        allKnowCards.add(ctx.mkInt(25)); //4,4
        allKnowCards.add(ctx.mkInt(13)); //2,6
        allKnowCards.add(ctx.mkInt(9));  //2,2

        BoolExpr boolExpr = ctx.mkAnd(ctx.mkLe(ctx.mkInt(1), killCard), ctx.mkLe(killCard, ctx.mkInt(28)));
        condition.add(boolExpr);

        IntExpr[] revert = new IntExpr[allKnowCards.size()];
        BoolExpr initialCondition = ctx.mkDistinct(allKnowCards.toArray(revert));
        condition.add(initialCondition);

        BoolExpr allResult = ctx.mkTrue();

        for(Card card : allUnknowCards){
            List<List<Card>> allPossibleCard = getPossibleCard(card);
            List<BoolExpr> orCondition = new ArrayList<>();
            for (List<Card> cards : allPossibleCard){
                revert = new IntExpr[allKnowCards.size()+2];
                allKnowCards.toArray(revert);
                revert[allKnowCards.size()] = ctx.mkInt((cards.get(0).getType()-1)*7 +cards.get(0).getNo());
                revert[allKnowCards.size()+1] = ctx.mkInt((cards.get(1).getType()-1)*7 +cards.get(1).getNo());
                BoolExpr trupleExpr = ctx.mkDistinct(revert);
                orCondition.add(trupleExpr);
            }
            BoolExpr orResult = orCondition.get(0);
            for(int i=1;i<orCondition.size();i++){
                orResult = ctx.mkOr(orResult,orCondition.get(i));
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
            System.out.println("<"+((num-1)/7+1)+","+((num-1)%7+1)+">");
        }
    }

    public List<List<Card>> getPossibleCard(Card card) {
        List<List<Card>> allPossibleCard = new ArrayList<>();
        if (card.getNo() == 1 || card.getNo() == 7) {

        } else if (card.getNo() == 6) {  //只有两种可能性
            List<Card> list1 = new ArrayList<>();
            Card card1 = new Card(card.getType(),5);
            Card card2 = new Card(card.getType(),7);
            list1.add(card1);
            list1.add(card2);
            List<Card> list2 = new ArrayList<>();
            Card card3 = new Card(card.getType(),4);
            Card card4 = new Card(card.getType(),5);
            list2.add(card3);
            list2.add(card4);
            allPossibleCard.add(list1);
            allPossibleCard.add(list2);
        } else if (card.getNo() == 2) {//只有两种可能性
            List<Card> list1 = new ArrayList<>();
            Card card1 = new Card(card.getType(),1);
            Card card2 = new Card(card.getType(),3);
            list1.add(card1);
            list1.add(card2);
            List<Card> list2 = new ArrayList<>();
            Card card3 = new Card(card.getType(),3);
            Card card4 = new Card(card.getType(),4);
            list2.add(card3);
            list2.add(card4);
            allPossibleCard.add(list1);
            allPossibleCard.add(list2);
        } else { //三种可能性
            List<Card> list1 = new ArrayList<>();
            Card card1 = new Card(card.getType(),card.getNo()-1);
            Card card2 = new Card(card.getType(),card.getNo()-2);
            list1.add(card1);
            list1.add(card2);
            List<Card> list2 = new ArrayList<>();
            Card card3 = new Card(card.getType(),card.getNo()-1);
            Card card4 = new Card(card.getType(),card.getNo()+1);
            list2.add(card3);
            list2.add(card4);
            List<Card> list3 = new ArrayList<>();
            Card card5 = new Card(card.getType(),card.getNo()+2);
            Card card6 = new Card(card.getType(),card.getNo()+1);
            list3.add(card5);
            list3.add(card6);

            allPossibleCard.add(list1);
            allPossibleCard.add(list2);
            allPossibleCard.add(list3);
        }
        return allPossibleCard;
    }

}
