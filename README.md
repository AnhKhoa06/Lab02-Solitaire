# Lab 02 - Solitaire: Fixed Winnable Deck

## Yêu cầu bài tập
Viết lại class `Deck` để thay vì tạo bộ bài xáo ngẫu nhiên (shuffled), nó tạo ra bộ bài theo một **thứ tự cố định, sắp đặt trước**, đảm bảo ván chơi luôn giải được (thắng được).

File đã thay đổi: [`src/ca/mcgill/solitaire/cards/Deck.java`](src/ca/mcgill/solitaire/cards/Deck.java)

## Ý tưởng thuật toán

Cơ chế chia bài ban đầu (`Tableau.initialize()`) lấy 28 lá đầu tiên từ `Deck`, chia vào 7 cột Tableau (1, 2, 3, ..., 7 lá/cột), lá cuối mỗi cột được lật ngửa. 24 lá còn lại nằm trong Stock, rút dần từng lá.

**Mục tiêu thiết kế**: sắp đặt 52 lá sao cho tại bất kỳ thời điểm nào, lá bài đang "lộ ra" (đỉnh 1 trong 7 cột, hoặc lá vừa rút từ Stock) đều có thể đưa **thẳng** lên Foundation ngay lập tức — không bao giờ cần kéo bài qua lại giữa các cột.

**Cách thực hiện**:
1. Coi 7 cột Tableau + Stock là 8 "hàng đợi" riêng biệt, với sức chứa lần lượt 1, 2, 3, 4, 5, 6, 7 và 24.
2. Duyệt 52 lá theo thứ tự **tăng dần trong từng chất** (A, 2, 3, ..., K của từng Suit lần lượt).
3. Rải đều (round-robin) các lá này vào 8 hàng đợi theo đúng sức chứa.
4. Vì luôn duyệt tăng dần theo chất, mỗi hàng đợi tự động chứa các lá cùng chất theo đúng thứ tự tăng dần tại thời điểm chúng lộ ra → bất kể lá nào lộ ra cũng luôn đúng là lá Foundation đang cần.
5. Quy đổi từ "thứ tự lộ ra mong muốn" sang "thứ tự deal/draw thực tế": đảo ngược từng cột Tableau (vì lá chia cuối cùng lại lộ ra đầu tiên), giữ nguyên thứ tự Stock, rồi đảo ngược toàn bộ danh sách một lần nữa (do `CardStack` xem phần tử đầu danh sách là đáy stack).

## Cách chơi để thắng
Chạy ứng dụng, sau đó **luôn đưa bất kỳ lá nào đang lộ ra lên Foundation ngay khi hợp lệ** (không cần kéo bài giữa các cột). Dùng phím **Enter** (auto-play) lặp lại nhiều lần — chương trình sẽ tự chơi và thắng hoàn toàn theo đúng chiến lược trên; khi bí thì rút thêm bài từ Stock rồi tiếp tục nhấn Enter.

## Kết quả
Đã test chạy thực tế: ván chơi kết thúc với thông báo **"You won!"**, cả 4 khay Foundation đều đầy đủ 13 lá (tới K) cho mỗi chất.

---

# Solitaire
Implementation of a [Solitaire card game](https://en.wikipedia.org/wiki/Patience_(game)) with [JavaFX](https://openjfx.io/).

Demonstration application used in the book [Introduction to Software Design with Java, 2nd edition](https://link.springer.com/book/10.1007/978-3-030-97899-0).
[Release 1.2](https://github.com/prmr/Solitaire/tree/v1.2) is consistent with the [2nd edition](https://link.springer.com/book/10.1007/978-3-030-97899-0) of the book. [Release 1.0](https://github.com/prmr/Solitaire/tree/v1.0) is consistent with the [1st edition](https://www.springer.com/us/book/9783030240936).

![Screenshot of the Solitaire application](Solitaire.png)

## Building the Application

This repository is configured to build automatically in Eclipse with Java 21 and JavaFX 21.

However, when first imported, the project will show a compilation error because the JavaFX dependency is missing.

To add JavaFX:

1. Download [JavaFX 21](https://jdk.java.net/javafx21/);
2. Create a new `User Library` under `Eclipse -> Window -> Preferences -> Java -> Build Path -> User Libraries -> New`. Name it `JavaFX21` and include the jars under the `lib` folder from the location where you extracted the JavaFX download.

The project should then build properly.

## Running the Application

Right-click on the project and select `Run As -> Java Application`. Select `Solitaire` from the list.

To run the tests, select `Run As - > JUnit Test`.

There are also two driver programs, `Driver` and `CrashTest`, which run the application in headless mode (that is, without the GUI).

## Playing the Game

* Click on the deck of cards (top left) to draw/reveal a new card.
* Drag and drop cards to the _foundation piles_ (at the top) or to piles in the _tableau_ (at the bottom). Cards in foundations piles must be of the same suit, in strictly consecutive ascending rank. Cards in the tableau must be in strictly consecutive decreasing rank, but in alternating suit color, as illustrated above.
* Press <kbd>Enter</kbd> to automatically play a move.
* Press <kbd>Backspace</kbd> to undo the last move.