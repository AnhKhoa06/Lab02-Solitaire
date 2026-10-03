package ca.mcgill.solitaire.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a deck of 52 cards arranged in a pre-determined order
 * that guarantees the resulting game of Solitaire can be won.
 */
public class Deck {
	private static final int[] TABLEAU_PILE_SIZES = {1, 2, 3, 4, 5, 6, 7};
	private static final int STOCK_SIZE = 24; // 52 - (1+2+...+7)

	private CardStack aCards;

	public Deck() {
		shuffle();
	}

	/**
	 * [LAB 02] Đã sửa: thay vì xáo bài ngẫu nhiên (Collections.shuffle),
	 * hàm này tạo bộ bài theo 1 thứ tự CỐ ĐỊNH (buildWinnableOrder()),
	 * đảm bảo ván chơi luôn thắng được.
	 */
	public void shuffle() {
		aCards = new CardStack(buildWinnableOrder());
	}

	/*
	 * [LAB 02] Ý tưởng: coi 7 cột Tableau + Stock là 8 "hàng đợi", rải đều
	 * (round-robin) 52 lá (duyệt tăng dần theo từng chất: A,2,...,K) vào
	 * 8 hàng đợi theo đúng sức chứa (1,2,...,7 và 24). Nhờ vậy mỗi hàng
	 * đợi luôn có các lá cùng chất theo đúng thứ tự tăng dần -> lá nào
	 * lộ ra cũng luôn đúng là lá Foundation đang cần, không cần kéo bài
	 * qua lại giữa các cột.
	 *
	 * Sau đó quy đổi sang thứ tự deal thực tế: đảo ngược từng cột (lá
	 * lộ ra trước phải rút sau cùng), giữ nguyên thứ tự Stock, rồi đảo
	 * ngược toàn bộ 1 lần nữa (CardStack coi đầu danh sách là đáy).
	 */
	private static List<Card> buildWinnableOrder() {
		int[] capacities = {1, 2, 3, 4, 5, 6, 7, STOCK_SIZE};
		List<List<Card>> accessOrder = new ArrayList<>();
		for (int i = 0; i < capacities.length; i++) {
			accessOrder.add(new ArrayList<>());
		}

		List<Card> ascendingBySuit = new ArrayList<>();
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				ascendingBySuit.add(Card.get(rank, suit));
			}
		}

		int queue = 0;
		for (Card card : ascendingBySuit) {
			while (accessOrder.get(queue).size() >= capacities[queue]) {
				queue = (queue + 1) % capacities.length;
			}
			accessOrder.get(queue).add(card);
			queue = (queue + 1) % capacities.length;
		}

		List<Card> byDrawOrder = new ArrayList<>();
		for (int pile = 0; pile < TABLEAU_PILE_SIZES.length; pile++) {
			List<Card> dealOrder = new ArrayList<>(accessOrder.get(pile));
			Collections.reverse(dealOrder);
			byDrawOrder.addAll(dealOrder);
		}
		byDrawOrder.addAll(accessOrder.get(accessOrder.size() - 1));

		Collections.reverse(byDrawOrder);
		return byDrawOrder;
	}

	public void push(Card pCard) {
		assert pCard != null;
		aCards.push(pCard);
	}

	public Card draw() {
		assert !isEmpty();
		return aCards.pop();
	}

	public boolean isEmpty() {
		return aCards.isEmpty();
	}
}