package com.infosmart.portal.util;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

/**
 * <p>
 * Title: 算术表达式求值演示
 * </p>
 * 
 * <p>
 * Description: 以字符序列的形式从终端输入语法正确的,不含变量的整数表达式。 利用算符优先关系实现对算术四则混合运算表达式的求值，
 * 并演示在求值中运算符栈，运算数栈，输入字符和主要操作的变化过程。
 * 
 * 
 * 符号比栈顶优先级低的则处理 详细见书 P52
 * </p>
 * 
 * <p>
 * Author: JTL Date: 2006-08-30 page: www.hartech.cn
 * </p>
 */
public class Engine {

	protected final static Logger logger = Logger.getLogger(Engine.class);

	// 运算符号检索
	public static String index = "+-*/()";

	// 比较 c1 与 c2 优先级：
	// precede[index.indexOf(c1)][index.indexOf(c2)]
	public static char[][] precede = { {
			// + - * / ( )
			/* + */'>', '>', '<', '<', '<', '>' }, {
	/*-*/'>', '>', '<', '<', '<', '>' }, {
	/***/
	'>', '>', '>', '>', '<', '>' }, {
	/* / */'>', '>', '>', '>', '<', '>' }, {
	/* ( */'<', '<', '<', '<', '<', '=' }, {
	/* ) */'>', '>', '>', '>', ' ', '>' } };

	public static String playExpression(String in) {
		if (!StringUtils.notNullAndSpace(in)) {
			return "NAN";
		}
		/*
		 * if (in.toUpperCase().indexOf("E") != -1) { logger.info("要转换的原始数据：" +
		 * in); logger.info("科学计数法转换:" + new BigDecimal(in).toPlainString());
		 * return new BigDecimal(in).toPlainString(); }
		 */
		// 头尾加入()，方便判断是否计算完毕
		in = '(' + in + ')';

		char c;
		Stack stack_num = new Stack();
		Stack stack_symbol = new Stack();

		// 下面两个用于临时记录多位数字，如 245、55.200
		Queue queue = new Queue();
		StringBuffer temp = new StringBuffer();

		stack_symbol.push(in.charAt(0));

		// 用i遍历该字符串
		for (int i = 1; i < in.length(); i++) {
			c = in.charAt(i);

			// 为空格，忽略掉，解析下一个
			if (c == ' ') {

				// 为数字
			} else if (index.indexOf(c) == -1) {

				// 数字可能为多位，一一入队
				queue.enQueue(c);
			}
			// 为符号
			else {

				// 若符号前为数字，把数字转成String
				while (!queue.isEmpty()) {
					temp.append(queue.deQueue());
				}

				// 把该数字入栈
				if (temp.length() > 0) {
					if (temp.toString().toUpperCase().indexOf("E") != -1) {
						// 处理科学计算法的数字
						stack_num.push(new BigDecimal(temp.toString())
								.toPlainString());
					} else {
						stack_num.push(Double.parseDouble(temp.toString()));
					}
					temp.setLength(0);
				}

				// 处理i所指向符号
				switch (precede[index.indexOf(((Character) stack_symbol
						.getTop()).charValue())][index.indexOf(c)]) {
				case '<': // c 优先级高，入栈

					stack_symbol.push(c);
					break;

				case '=': // 脱括号()

					stack_symbol.pop();

					// 若符号栈为空，计算完成
					if (stack_symbol.isEmpty()) {
						return stack_num.pop().toString();
					}
					break;

				case '>': // c 优先级低，出栈、计算、结果入栈

					stack_num.push(count(stack_num.pop(), stack_symbol.pop(),
							stack_num.pop()));

					// 使 i 仍指向现在的符号，看看前面是否仍有优先级比其高的
					i--;
					break;
				}
			}
		}
		return "NAN";
	}

	public static double count(Object a, Object symbol, Object b) {
		// a 与 b 要互换
		double nb = Double.parseDouble(a.toString());
		double na = Double.parseDouble(b.toString());
		char sym = ((Character) symbol).charValue();
		switch (sym) {
		case '+':
			return na + nb;
		case '-':
			return na - nb;
		case '*':
			return na * nb;
		case '/':
			return na / nb;
		}
		return 0;
	}

}
