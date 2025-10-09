package com.example.iel

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WordAdapter

    // IELTS 단어 리스트 (100개) → 25개씩 PART 구분
    val words = listOf(
        // === PART 1 ===
        Word("Executive", "명사: 경영진"),
        Word("Inventory", "명사: 재고"),
        Word("Feature", "명사: 특징"),
        Word("Acknowledge", "동사: 수신을 인정하다"),
        Word("Voucher", "명사: 상품권"),
        Word("Estimate", "명사: 견적서"),
        Word("Resume", "동사: 재개하다"),
        Word("Issue", "명사: 문제"),
        Word("Property", "명사: 재산"),
        Word("Eligible", "형용사: 자격이 있는"),
        Word("Initiative", "명사: 새로운 계획"),
        Word("Culinary", "형용사: 요리의"),
        Word("Extensive", "형용사: 광범위한"),
        Word("Deposit", "명사: 예치금"),
        Word("Retail", "명사: 소매"),
        Word("Affordable", "형용사: 가격이 알맞은"),
        Word("Grant", "동사: 승인하다"),
        Word("Significantly", "부사: 상당히"),
        Word("Reserve", "동사: 예약하다"),
        Word("Application", "명사: 신청"),
        Word("Address", "명사: 주소"),
        Word("Suppose", "동사: 추정하다"),
        Word("Charge", "명사: 요금"),
        Word("Expire", "동사: 기한이 만료되다"),
        Word("Refund", "명사: 환불"),
        // === PART 2 ===
        Word("Promote", "동사: 촉진하다"),
        Word("Revenue", "명사: 수익"),
        Word("Opportunity", "명사: 기회"),
        Word("Valid", "형용사: 유효한"),
        Word("Confidential", "형용사: 기밀의"),
        Word("Figure", "명사: 숫자"),
        Word("Traffic", "명사: 교통"),
        Word("Audience", "명사: 청중"),
        Word("Occupy", "동사: 공간을 차지하다"),
        Word("Approximately", "부사: 대략"),
        Word("Complimentary", "형용사: 무료의"),
        Word("Outstanding", "형용사: 미해결의"),
        Word("Imperative", "형용사: 긴급한"),
        Word("Substantial", "형용사: 상당한"),
        Word("Decline", "동사: 감소하다"),
        Word("Balance", "명사: 균형"),
        Word("Account", "명사: 계좌"),
        Word("Proceed", "동사: 계속하다"),
        Word("Amenities", "명사: 편의시설"),
        Word("Renowned", "형용사: 유명한"),
        Word("Preserve", "동사: 보존하다"),
        Word("Simply", "부사: 단순하게"),
        Word("Presence", "명사: 존재"),
        Word("Laboratory", "명사: 실험실"),
        Word("Effective", "형용사: 효과적인"),
        // === PART 3 ===
        Word("Enhance", "동사: 향상시키다"),
        Word("Insurance", "명사: 보험"),
        Word("Spacious", "형용사: 넓은"),
        Word("Ingredient", "명사: 재료"),
        Word("Withdraw", "동사: 철회하다"),
        Word("Banquet", "명사: 연회"),
        Word("Experiment", "명사: 실험"),
        Word("Specific", "형용사: 구체적인"),
        Word("Specialize", "동사: 전문화하다"),
        Word("Affect", "동사: 영향을 미치다"),
        Word("Equip", "동사: 장비를 제공하다"),
        Word("Reimbursement", "명사: 환급"),
        Word("Premises", "명사: 건물과 토지"),
        Word("Renovate", "동사: 수리하다"),
        Word("Versatile", "형용사: 다재다능한"),
        Word("Implement", "동사: 실행하다"),
        Word("Informed", "형용사: 정보에 기반한"),
        Word("Commitment", "명사: 약속"),
        Word("Direct", "형용사: 직접적인"),
        Word("Facilitate", "동사: 용이하게 하다"),
        // === PART 4 ===
        Word("Commission", "명사: 수수료"),
        Word("Cover", "동사: 덮다"),
        Word("Encourage", "동사: 격려하다"),
        Word("Persuade", "동사: 설득하다"),
        Word("Accompany", "동사: 동반하다"),
        Word("Candidate", "명사: 후보자"),
        Word("Renew", "동사: 갱신하다"),
        Word("Launch", "동사: 출시하다"),
        Word("Reliable", "형용사: 믿을 수 있는"),
        Word("Inform", "동사: 알리다"),
        Word("Contribute", "동사: 기여하다"),
        Word("Strategy", "명사: 전략"),
        Word("Motivate", "동사: 동기부여하다"),
        Word("Consider", "동사: 고려하다"),
        Word("Remind", "동사: 상기시키다"),
        Word("Suitable", "형용사: 적합한"),
        Word("Beverage", "명사: 음료"),
        Word("Portable", "형용사: 휴대할 수 있는"),
        Word("Essential", "형용사: 필수적인"),
        Word("Consent", "명사: 동의"),
        Word("Tentative", "형용사: 잠정적인"),
        Word("Respective", "형용사: 각각의"),
        Word("Arrange", "동사: 정리하다"),
        Word("Associate", "동사: 관련시키다"),
        Word("Publicity", "명사: 언론 보도"),
        Word("Instructor", "명사: 강사"),
        Word("Contractor", "명사: 계약자"),
        Word("Relevant", "형용사: 관련 있는"),
        Word("Weekly", "형용사: 매주 열리는"),
        Word("Imply", "동사: 암시하다")
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // RecyclerView 설정
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 전달받은 part 번호 (기본값 1)
        val partNumber = intent.getIntExtra("partNumber", 1)

        // 25개씩 분할해서 부분 리스트 생성
        val startIndex = (partNumber - 1) * 25
        val endIndex = minOf(startIndex + 25, words.size)
        val partWords = words.subList(startIndex, endIndex)

        // 어댑터 설정
        adapter = WordAdapter(partWords)
        recyclerView.adapter = adapter

        // 퀴즈 버튼 클릭 시 QuizActivity로 단어 리스트 전달하며 이동
        val quizButton: ImageButton = findViewById(R.id.quizButton)
        quizButton.setOnClickListener {
            val wordsForQuiz = ArrayList(partWords)  // ArrayList로 변환 (Serializable)

            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("words", wordsForQuiz)
            startActivity(intent)
        }

        // Toolbar 설정
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = "PART $partNumber"
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        // Edge-to-Edge 적용
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
