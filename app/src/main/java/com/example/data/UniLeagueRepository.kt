package com.example.data

import com.example.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.*

class UniLeagueRepository {

  // Demo User Profiles
  val studentDemoUser = UserProfile(
    id = "user_student_1",
    name = "شهد الأحمدي",
    email = "shahd.student@ksu.edu.sa",
    role = UserRole.STUDENT,
    avatar = "👩‍🎓",
    university = "جامعة الملك سعود",
    college = "كلية علوم الحاسب والمعلومات",
    department = "تقنية المعلومات",
    major = "تقنية وتطوير البرمجيات",
    academicLevel = "المستوى الخامس - سنة 3",
    points = 5200,
    levelNumber = 3,
    levelTitle = "متقدم",
    rankUniversity = 3,
    rankCollege = 2,
    rankDepartment = 1,
    completedChallengesCount = 12,
    unlockedAchievementsCount = 5,
    hoursSpent = 24.5f
  )

  val teacherDemoUser = UserProfile(
    id = "user_teacher_1",
    name = "د. عبد الرحمن الخالدي",
    email = "a.alkhaldi@ksu.edu.sa",
    role = UserRole.TEACHER,
    avatar = "👨‍🏫",
    university = "جامعة الملك سعود",
    college = "كلية علوم الحاسب والمعلومات",
    department = "علوم الحاسب",
    major = "هندسة البرمجيات والذكاء الاصطناعي",
    academicLevel = "أستاذ مشارك",
    points = 9800,
    levelNumber = 5,
    levelTitle = "خبير",
    rankUniversity = 1,
    rankCollege = 1,
    rankDepartment = 1
  )

  val adminDemoUser = UserProfile(
    id = "user_admin_1",
    name = "أ. نورة الشمري",
    email = "admin.league@ksu.edu.sa",
    role = UserRole.ADMIN,
    avatar = "🏛️",
    university = "جامعة الملك سعود",
    college = "عمادة القبول وشؤون الطلاب",
    department = "إدارة المنصات الأكاديمية",
    major = "إدارة نظم المعلومات",
    academicLevel = "مشرف النظام",
    points = 12000,
    levelNumber = 5,
    levelTitle = "خبير"
  )

  // Mutable State Flows
  private val _currentUser = MutableStateFlow<UserProfile?>(studentDemoUser)
  val currentUser: StateFlow<UserProfile?> = _currentUser.asStateFlow()

  private val _skills = MutableStateFlow<List<SkillItem>>(emptyList())
  val skills: StateFlow<List<SkillItem>> = _skills.asStateFlow()

  private val _challenges = MutableStateFlow<List<Challenge>>(emptyList())
  val challenges: StateFlow<List<Challenge>> = _challenges.asStateFlow()

  private val _attempts = MutableStateFlow<List<ChallengeAttempt>>(emptyList())
  val attempts: StateFlow<List<ChallengeAttempt>> = _attempts.asStateFlow()

  private val _achievements = MutableStateFlow<List<Achievement>>(emptyList())
  val achievements: StateFlow<List<Achievement>> = _achievements.asStateFlow()

  private val _competitions = MutableStateFlow<List<Competition>>(emptyList())
  val competitions: StateFlow<List<Competition>> = _competitions.asStateFlow()

  private val _teams = MutableStateFlow<List<Team>>(emptyList())
  val teams: StateFlow<List<Team>> = _teams.asStateFlow()

  private val _departmentRankings = MutableStateFlow<List<DepartmentRanking>>(emptyList())
  val departmentRankings: StateFlow<List<DepartmentRanking>> = _departmentRankings.asStateFlow()

  private val _notifications = MutableStateFlow<List<AppNotification>>(emptyList())
  val notifications: StateFlow<List<AppNotification>> = _notifications.asStateFlow()

  private val _userAccounts = MutableStateFlow<List<UserAccount>>(emptyList())
  val userAccounts: StateFlow<List<UserAccount>> = _userAccounts.asStateFlow()

  private val _aiRecommendation = MutableStateFlow<AIRecommendation?>(null)
  val aiRecommendation: StateFlow<AIRecommendation?> = _aiRecommendation.asStateFlow()

  init {
    loadSeedData()
  }

  private fun loadSeedData() {
    _skills.value = listOf(
      SkillItem("sk_1", "البرمجة والخوارزميات", "Programming", "Software", 85, "جيد جدًا", "Very Good", "code"),
      SkillItem("sk_2", "قواعد البيانات SQL", "Database", "Data", 72, "جيد", "Good", "database"),
      SkillItem("sk_3", "الشبكات والاتصالات", "Networking", "Infrastructure", 60, "متوسط", "Medium", "wifi"),
      SkillItem("sk_4", "الأمن السيبراني", "Cyber Security", "Security", 48, "يحتاج إلى تطوير", "Needs Work", "shield"),
      SkillItem("sk_5", "تحليل واستخراج البيانات", "Data Analysis", "Data", 78, "جيد جدًا", "Very Good", "analytics"),
      SkillItem("sk_6", "تطوير تطبيقات الويب", "Web Development", "Software", 88, "ممتاز", "Excellent", "web"),
      SkillItem("sk_7", "تطوير تطبيقات الجوال", "Mobile Development", "Software", 82, "جيد جدًا", "Very Good", "phone"),
      SkillItem("sk_8", "الذكاء الاصطناعي", "Artificial Intelligence", "AI", 68, "جيد", "Good", "psychology"),
      SkillItem("sk_9", "تصميم واجهات المستخدم UI/UX", "UI/UX", "Design", 92, "ممتاز", "Excellent", "palette"),
      SkillItem("sk_10", "حل المشكلات المعقدة", "Problem Solving", "Core", 80, "جيد جدًا", "Very Good", "extension")
    )

    _challenges.value = listOf(
      Challenge(
        id = "ch_sql_1",
        title = "قواعد البيانات واستعلامات SQL",
        description = "اختبر مهاراتك في استعلامات JOIN، التجميع GROUP BY، والفهارس المعقدة في بيئة قواعد البيانات العلائقية.",
        skillCategory = "قواعد البيانات SQL",
        major = "تقنية المعلومات",
        difficulty = "Medium",
        durationMinutes = 20,
        points = 500,
        participantsCount = 142,
        isTeam = false,
        questions = listOf(
          Question(
            id = "q_sql_1",
            text = "أي أمر SQL يُستخدم لدمج السجلات فقط عندما يوجد تطابق في كلا الجدولين؟",
            options = listOf("INNER JOIN", "LEFT JOIN", "FULL OUTER JOIN", "CROSS JOIN"),
            correctOptionIndex = 0,
            points = 100
          ),
          Question(
            id = "q_sql_2",
            text = "ما هي العبارة المستخدمة لتصفية نتائج دوال التجميع (Aggregate Functions)؟",
            options = listOf("WHERE", "HAVING", "GROUP BY", "ORDER BY"),
            correctOptionIndex = 1,
            points = 100
          ),
          Question(
            id = "q_sql_3",
            text = "أي نوع فهرس (Index) يضمن ترتيب البيانات الفيزيائي على القرص في قاعدة البيانات؟",
            options = listOf("Non-Clustered Index", "Clustered Index", "Bitmap Index", "Filtered Index"),
            correctOptionIndex = 1,
            points = 100
          ),
          Question(
            id = "q_sql_4",
            text = "ما الخاصية في معيار ACID التي تضمن بقاء التغييرات حتى عند انقطاع النظام فجأة؟",
            options = listOf("Atomicity (الذرية)", "Consistency (الاتساق)", "Isolation (العزل)", "Durability (الديمومة)"),
            correctOptionIndex = 3,
            points = 100
          ),
          Question(
            id = "q_sql_5",
            text = "ما ناتج استعلام COUNT(*) على جدول يحتوي 5 صفوف منها قيم NULL؟",
            options = listOf("0", "يستثني قيم NULL", "5 صفوف كاملة", "يعطي خطأ في بناء الجملة"),
            correctOptionIndex = 2,
            points = 100
          )
        )
      ),
      Challenge(
        id = "ch_cyber_1",
        title = "أساسيات الأمن السيبراني والتشفير",
        description = "تحدي تكتيكي لتحليل الثغرات الشائعة (OWASP Top 10)، التشفير المتماثل وغير المتماثل وهجمات الهندسة الاجتماعية.",
        skillCategory = "الأمن السيبراني",
        major = "الأمن السيبراني وتقنية المعلومات",
        difficulty = "Hard",
        durationMinutes = 25,
        points = 650,
        participantsCount = 98,
        isTeam = false,
        questions = listOf(
          Question(
            id = "q_cy_1",
            text = "أي خوارزمية تشفير تعتمد على المفتاح العام والمفتاح الخاص (غير متماثلة)؟",
            options = listOf("AES-256", "DES", "RSA", "Blowfish"),
            correctOptionIndex = 2,
            points = 130
          ),
          Question(
            id = "q_cy_2",
            text = "ما هي الطريقة الفعالة لمنع هجمات حقن قواعد البيانات SQL Injection؟",
            options = listOf("التحقق بالـ Javascript فقط", "استخدام Prepared Statements والمحددات", "تشفير كلمة المرور بالـ MD5", "تعطيل جدار الحماية"),
            correctOptionIndex = 1,
            points = 130
          ),
          Question(
            id = "q_cy_3",
            text = "ما الهدف من هجوم حجب الخدمة الموزع (DDoS)؟",
            options = listOf("سرقة البيانات المشفرة", "إغراق الخادم بحركة مرور زائفة لتعطيله", "تعديل محتوى الصفحة", "تخمين كلمات المرور"),
            correctOptionIndex = 1,
            points = 130
          ),
          Question(
            id = "q_cy_4",
            text = "ما الفرق الجوهري بين Hashing و Encryption؟",
            options = listOf("التجزئة باتجاه واحد ولا يمكن عكسها", "التشفير باتجاه واحد فقط", "كلاهما متطابقان تماماً", "التجزئة تتطلب مفتاح سري دائماً"),
            correctOptionIndex = 0,
            points = 130
          ),
          Question(
            id = "q_cy_5",
            text = "أي نموذج يصف مبادئ أمن المعلومات الثلاثية الأساسية؟",
            options = listOf("CIA Triad", "OSI Model", "TCP/IP Stack", "CRUD Model"),
            correctOptionIndex = 0,
            points = 130
          )
        )
      ),
      Challenge(
        id = "ch_prog_1",
        title = "خوارزميات وهياكل البيانات المتقدمة",
        description = "تحدي لحل المسائل المعقدة، تحليل التعقيد الزمني Big-O، والتعامل مع الأشجار الثنائية والبرمجة الديناميكية.",
        skillCategory = "البرمجة والخوارزميات",
        major = "علوم الحاسب",
        difficulty = "Expert",
        durationMinutes = 30,
        points = 800,
        participantsCount = 76,
        isTeam = false,
        questions = listOf(
          Question(
            id = "q_algo_1",
            text = "ما هو التعقيد الزمني في الحالة المتوسطة لخوارزمية Merge Sort؟",
            options = listOf("O(N log N)", "O(N^2)", "O(N)", "O(log N)"),
            correctOptionIndex = 0,
            points = 200
          ),
          Question(
            id = "q_algo_2",
            text = "ما الهيكل البياني الأنسب لتطبيق خوارزمية البحث بالعرض أولاً (BFS)؟",
            options = listOf("Stack (المكدس)", "Queue (طابور الانتظار)", "Binary Heap", "Hash Table"),
            correctOptionIndex = 1,
            points = 200
          ),
          Question(
            id = "q_algo_3",
            text = "ما الخاصية المميزة لشجرة البحث الثنائية المتوازنة (AVL Tree)؟",
            options = listOf("فرق الارتفاع بين الشجرتين الفرعيتين لا يتجاوز 1", "كل عقدة لها 3 أبناء", "تخزين المفاتيح عشوائياً", "لا تدعم الحذف"),
            correctOptionIndex = 0,
            points = 200
          ),
          Question(
            id = "q_algo_4",
            text = "في البرمجة الديناميكية، ما الهدف الرئيسي من تقنية Memoization؟",
            options = listOf("تخزين نتائج المسائل الفرعية لتجنب إعادة الحساب", "ضغط البيانات في الذاكرة", "تنفيذ الأكواد بالتوازي", "إلغاء الاستدعاء الذاتي"),
            correctOptionIndex = 0,
            points = 200
          )
        )
      ),
      Challenge(
        id = "ch_web_1",
        title = "هندسة تطبيقات الويب الحديثة",
        description = "تحدي يركز على React/Next.js، واجهات RESTful و GraphQL، وإدارة الحالة وأمان الويب.",
        skillCategory = "تطوير تطبيقات الويب",
        major = "تقنية المعلومات",
        difficulty = "Easy",
        durationMinutes = 15,
        points = 350,
        participantsCount = 210,
        isTeam = false,
        questions = listOf(
          Question(
            id = "q_web_1",
            text = "ما هو رمز الاستجابة HTTP الدال على نجاح إنشاء مورد جديد على الخادم؟",
            options = listOf("200 OK", "201 Created", "204 No Content", "301 Redirect"),
            correctOptionIndex = 1,
            points = 70
          ),
          Question(
            id = "q_web_2",
            text = "أي رأس (Header) في HTTP يُستخدم لمنع هجمات Cross-Origin Resource Sharing غير المصرحة؟",
            options = listOf("Access-Control-Allow-Origin", "Content-Security-Policy", "X-Frame-Options", "Authorization"),
            correctOptionIndex = 0,
            points = 70
          ),
          Question(
            id = "q_web_3",
            text = "ما فائدة الـ Virtual DOM في مكتبات الويب الحديثة مثل React؟",
            options = listOf("تقليل التحديثات المباشرة للـ DOM الفعلي لتحسين الأداء", "تشفير اتصالات المتصفح", "استبدال محرك الجافاسكريبت", "تخزين الكوكيز بشكل آمن"),
            correctOptionIndex = 0,
            points = 70
          ),
          Question(
            id = "q_web_4",
            text = "أي صيغة بيانات تُعد المعيار الأوسع استخداماً في تبادل البيانات عبر واجهات RESTful APIs؟",
            options = listOf("XML", "JSON", "YAML", "CSV"),
            correctOptionIndex = 1,
            points = 70
          ),
          Question(
            id = "q_web_5",
            text = "ما الهدف من استخدام Web Storage API (LocalStorage) في المتصفح؟",
            options = listOf("حفظ بيانات العميل محلياً دون إرسالها مع كل طلب HTTP", "تشغيل قواعد بيانات SQL كبيرة", "استضافة سيرفرات ويب", "تشفير الشبكات المحلية"),
            correctOptionIndex = 0,
            points = 70
          )
        )
      ),
      Challenge(
        id = "ch_ai_1",
        title = "الذكاء الاصطناعي وتعلم الآلة",
        description = "مفاهيم التعلم الخاضع للإشراف، الشبكات العصبية، ومعالجة اللغات الطبيعية LLMs.",
        skillCategory = "الذكاء الاصطناعي",
        major = "ذكاء اصطناعي وعلوم الحاسب",
        difficulty = "Medium",
        durationMinutes = 20,
        points = 550,
        participantsCount = 115,
        isTeam = false,
        questions = listOf(
          Question(
            id = "q_ai_1",
            text = "أي نوع من تعلم الآلة يعتمد على مكافآت وعقوبات في بيئة تفاعلية؟",
            options = listOf("Supervised Learning", "Unsupervised Learning", "Reinforcement Learning (التعلم المعزز)", "Semi-supervised Learning"),
            correctOptionIndex = 2,
            points = 110
          ),
          Question(
            id = "q_ai_2",
            text = "ما المشكلة التي تحدث عندما يؤدي النموذج أداءً مبهراً على بيانات التدريب لكنه يفشل على البيانات الجديدة؟",
            options = listOf("Underfitting (نقص التوافق)", "Overfitting (الإفراط في التوافق)", "Vanishing Gradient", "Data Drift"),
            correctOptionIndex = 1,
            points = 110
          ),
          Question(
            id = "q_ai_3",
            text = "ما المعمارية الأساسية التي بنيت عليها النماذج اللغوية الكبيرة (LLMs) مثل GPT و Gemini؟",
            options = listOf("RNN", "CNN", "Transformer Architecture", "Markov Chains"),
            correctOptionIndex = 2,
            points = 110
          ),
          Question(
            id = "q_ai_4",
            text = "أي مقياس يُفضل استخدامه لتقييم مصنف البيانات غير المتوازنة (Imbalanced Classes)؟",
            options = listOf("Accuracy فقط", "F1-Score / Precision-Recall", "Loss Function", "Mean Squared Error"),
            correctOptionIndex = 1,
            points = 110
          ),
          Question(
            id = "q_ai_5",
            text = "ما دور دالة التنشيط (Activation Function) في الشبكة العصبية الاصطناعية؟",
            options = listOf("إدخال اللاخطية (Non-linearity) لتتعلم الشبكة أنماطاً معقدة", "تسريع تدفق الكهرباء في المعالج", "حذف الأوزان العشوائية", "تقليل حجم البيانات فقط"),
            correctOptionIndex = 0,
            points = 110
          )
        )
      )
    )

    _attempts.value = listOf(
      ChallengeAttempt("att_1", "ch_web_1", "هندسة تطبيقات الويب الحديثة", "user_student_1", 100, 5, 5, 350, 680, "2026-09-02", "تطوير تطبيقات الويب", "Easy"),
      ChallengeAttempt("att_2", "ch_prog_1", "خوارزميات وهياكل البيانات المتقدمة", "user_student_1", 75, 3, 4, 600, 1420, "2026-09-04", "البرمجة والخوارزميات", "Expert"),
      ChallengeAttempt("att_3", "ch_ai_1", "الذكاء الاصطناعي وتعلم الآلة", "user_student_1", 80, 4, 5, 440, 1100, "2026-09-06", "الذكاء الاصطناعي", "Medium")
    )

    _achievements.value = listOf(
      Achievement("ach_1", "أول تحدي", "First Step", "أكمل أول تحدٍ بنجاح وحقق نقاطك الأولى في المنصة.", "Completed the first skill challenge successfully.", "flag", true, "2026-09-02", 0xFF059669, "أكمل تحديًا واحدًا"),
      Achievement("ach_2", "7 أيام متتالية", "7-Day Streak", "المشاركة والتدريب اليومي لمدة 7 أيام متتالية دون انقطاع.", "Logged in and trained for 7 consecutive days.", "local_fire_department", true, "2026-09-07", 0xFFD97706, "حافظ على وتيرة 7 أيام"),
      Achievement("ach_3", "محترف البرمجة", "Code Master", "الحصول على أكثر من 1,500 نقطة في مهارات البرمجة والخوارزميات.", "Earned over 1,500 points in coding & algorithmic tracks.", "code", true, "2026-09-05", 0xFF2563EB, "أحرز 1500 نقطة برمجة"),
      Achievement("ach_4", "خبير قواعد البيانات", "SQL Wizard", "حل 5 تحديات SQL بدقة تتجاوز 90% واستخراج علاقات معقدة.", "Solved 5 SQL challenges with >90% precision.", "storage", false, null, 0xFF64748B, "حل 5 تحديات SQL ممتازة"),
      Achievement("ach_5", "بطل الأسبوع", "Weekly Champion", "تحقيق المركز الأول في الكلية خلال الترتيب الأسبوعي.", "Achieved 1st place in the college weekly standings.", "military_tech", true, "2026-09-07", 0xFFEAB308, "الوصول لقمة الكلية أسبوعياً"),
      Achievement("ach_6", "متصدر القسم", "Department Ace", "تصدر قائمة الشرف في قسم تقنية المعلومات.", "Top rank in Information Technology Department.", "school", true, "2026-09-06", 0xFF0D9488, "المركز الأول في قسمك"),
      Achievement("ach_7", "أفضل عضو فريق", "MVP Team Member", "المساهمة بأكبر عدد من النقاط في بطولة الفرق الجماعية.", "Top point contributor to your registered team.", "group_work", false, null, 0xFF64748B, "ساهم بأعلى نقاط لفريقك"),
      Achievement("ach_8", "درع الأمن السيبراني", "Cyber Shield", "إكمال مسار ثغرات وتشفير الأمن السيبراني بدرجة كاملة.", "Completed cyber security defense track with 100% score.", "security", false, null, 0xFF64748B, "درجة كاملة في الأمن السيبراني")
    )

    _competitions.value = listOf(
      Competition(
        id = "comp_1",
        title = "أولمبياد البرمجة الجامعي السنوي",
        description = "أكبر هاكاثون وتحدي برمجي بين كليات الحاسب، يتنافس فيه أكثر من 300 طالب في خوارزميات الذكاء الاصطناعي وتطوير الحلول الذكية.",
        skill = "البرمجة والخوارزميات",
        typeAr = "بين الكليات",
        typeEn = "Inter-College",
        date = "2026-09-18",
        startTime = "10:00 ص",
        endTime = "06:00 م",
        participantsCount = 312,
        points = 2500,
        rules = "الفرق من 3 طلاب، ممنوع استخدام الأكواد الجاهزة، التقييم فوري عبر المنظومة الآلية.",
        isJoined = true
      ),
      Competition(
        id = "comp_2",
        title = "دوري التقاط العلم السيبراني CTF",
        description = "مسابقة اختراق أخلاقي ودفاع سيبراني لحماية خوادم افتراضية وفك تشفير الأعلام الأمنية واستخراج المفاتيح.",
        skill = "الأمن السيبراني",
        typeAr = "جماعية",
        typeEn = "Teams",
        date = "2026-09-24",
        startTime = "01:00 م",
        endTime = "08:00 م",
        participantsCount = 184,
        points = 2000,
        rules = "يحظر استهداف بنية المسابقة الأساسية، يتم احتساب سرعة حل التحديات.",
        isJoined = false
      ),
      Competition(
        id = "comp_3",
        title = "سباق استعلامات البيانات الكبرى SQL Sprint",
        description = "بطولة سريعة لحل استعلامات تحليلية معقدة على مجموعات بيانات بالملايين مع تحسين سرعة الاستعلام Index Optimization.",
        skill = "قواعد البيانات SQL",
        typeAr = "بين الأقسام",
        typeEn = "Inter-Department",
        date = "2026-09-28",
        startTime = "04:00 م",
        endTime = "07:00 م",
        participantsCount = 145,
        points = 1500,
        rules = "مسابقة فردية لطلاب قسم تقنية المعلومات وعلوم الحاسب ونظم المعلومات.",
        isJoined = false
      )
    )

    _teams.value = listOf(
      Team("team_1", "CyberKnights 🛡️", "تقنية المعلومات", "⚔️", listOf("شهد الأحمدي", "ريان القحطاني", "سارة الغامدي"), 16400, 3, 1),
      Team("team_2", "DataWizards 🧙‍♂️", "علوم الحاسب", "✨", listOf("أحمد العتيبي", "عمر الشهري", "لين الحربي"), 15850, 2, 2),
      Team("team_3", "CodeTitans 💻", "نظم المعلومات", "⚡", listOf("فيصل الدوسري", "منيرة السبيعي", "خالد الزهراني"), 14200, 1, 3),
      Team("team_4", "AI Pioneers 🤖", "الذكاء الاصطناعي", "🧠", listOf("باسم المطيري", "هند البقمي", "سلطان العمري"), 13700, 1, 4)
    )

    _departmentRankings.value = listOf(
      DepartmentRanking("dep_1", "تقنية المعلومات", "كلية علوم الحاسب والمعلومات", 480, 15800, 1),
      DepartmentRanking("dep_2", "علوم الحاسوب", "كلية علوم الحاسب والمعلومات", 520, 14200, 2),
      DepartmentRanking("dep_3", "نظم المعلومات", "كلية علوم الحاسب والمعلومات", 390, 12600, 3),
      DepartmentRanking("dep_4", "الذكاء الاصطناعي", "كلية علوم الحاسب والمعلومات", 280, 11900, 4),
      DepartmentRanking("dep_5", "هندسة البرمجيات", "كلية علوم الحاسب والمعلومات", 310, 10800, 5)
    )

    _notifications.value = listOf(
      AppNotification("notif_1", "تحدي جديد متاح الآن! 🎯", "تمت إضافة تحدي 'قواعد البيانات واستعلامات SQL' بواسطة د. عبد الرحمن الخالدي.", "منذ 15 دقيقة", "challenge", false),
      AppNotification("notif_2", "تبقى 48 ساعة على أولمبياد البرمجة 🏆", "تأكد من مراجعة مواضيع الخوارزميات وتأكيد حضور فريقك CyberKnights.", "منذ ساعتين", "tournament", false),
      AppNotification("notif_3", "تهانينا! حققت إنجاز بطل الأسبوع 🥇", "أحسنت يا شهد، أنتِ في صدارة ترتيب كلية علوم الحاسب والمعلومات هذا الأسبوع.", "أمس", "badge", true),
      AppNotification("notif_4", "دعوة انضمام إلى فريق 🤝", "دعاك فريق DataWizards للمشاركة في دوري التقاط العلم CTF.", "منذ 3 أيام", "team", true)
    )

    _userAccounts.value = listOf(
      UserAccount("user_student_1", "شهد الأحمدي", "shahd.student@ksu.edu.sa", UserRole.STUDENT, "تقنية المعلومات", "نشط", 5200),
      UserAccount("user_student_2", "أحمد العتيبي", "ahmad.otaibi@ksu.edu.sa", UserRole.STUDENT, "علوم الحاسب", "نشط", 5800),
      UserAccount("user_student_3", "سارة الغامدي", "sara.ghamdi@ksu.edu.sa", UserRole.STUDENT, "تقنية المعلومات", "نشط", 5500),
      UserAccount("user_student_4", "عمر الشهري", "omar.shehri@ksu.edu.sa", UserRole.STUDENT, "نظم المعلومات", "نشط", 4950),
      UserAccount("user_teacher_1", "د. عبد الرحمن الخالدي", "a.alkhaldi@ksu.edu.sa", UserRole.TEACHER, "علوم الحاسب", "نشط", 9800),
      UserAccount("user_teacher_2", "د. حصة السديري", "h.alsudairy@ksu.edu.sa", UserRole.TEACHER, "تقنية المعلومات", "نشط", 8400),
      UserAccount("user_admin_1", "أ. نورة الشمري", "admin.league@ksu.edu.sa", UserRole.ADMIN, "إدارة المنصات الأكاديمية", "نشط", 12000)
    )

    refreshAIRecommendations()
  }

  fun switchUserRole(role: UserRole) {
    when (role) {
      UserRole.STUDENT -> _currentUser.value = studentDemoUser
      UserRole.TEACHER -> _currentUser.value = teacherDemoUser
      UserRole.ADMIN -> _currentUser.value = adminDemoUser
    }
  }

  fun login(role: UserRole): Boolean {
    switchUserRole(role)
    return true
  }

  fun registerUser(
    name: String,
    email: String,
    university: String,
    college: String,
    department: String,
    major: String,
    academicLevel: String,
    role: UserRole
  ) {
    val newUser = UserProfile(
      id = "user_${UUID.randomUUID().toString().take(8)}",
      name = name,
      email = email,
      role = role,
      avatar = if (role == UserRole.STUDENT) "🎓" else if (role == UserRole.TEACHER) "👨‍🏫" else "🏛️",
      university = university,
      college = college,
      department = department,
      major = major,
      academicLevel = academicLevel,
      points = 100,
      levelNumber = 1,
      levelTitle = "مبتدئ",
      rankUniversity = 45,
      rankCollege = 22,
      rankDepartment = 12,
      completedChallengesCount = 0,
      unlockedAchievementsCount = 0,
      hoursSpent = 0.5f
    )
    _currentUser.value = newUser
    _userAccounts.update { it + UserAccount(newUser.id, newUser.name, newUser.email, newUser.role, newUser.department, "نشط", newUser.points) }
  }

  fun logout() {
    _currentUser.value = null
  }

  fun updateCurrentUserProfile(name: String, university: String, college: String, department: String, major: String, academicLevel: String) {
    _currentUser.update { current ->
      current?.copy(
        name = name,
        university = university,
        college = college,
        department = department,
        major = major,
        academicLevel = academicLevel
      )
    }
  }

  fun submitChallengeAttempt(
    challengeId: String,
    correctCount: Int,
    totalCount: Int,
    timeSpentSeconds: Int
  ): ChallengeAttempt {
    val challenge = _challenges.value.find { it.id == challengeId } ?: _challenges.value.first()
    val scorePercent = if (totalCount > 0) ((correctCount.toFloat() / totalCount) * 100).toInt() else 0

    // Balanced points formula based on accuracy: base points + performance multiplier
    val pointsEarned = ((challenge.points * (scorePercent / 100f))).toInt().coerceAtLeast(50)

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val attempt = ChallengeAttempt(
      id = "att_${UUID.randomUUID().toString().take(6)}",
      challengeId = challenge.id,
      challengeTitle = challenge.title,
      userId = _currentUser.value?.id ?: "guest",
      scorePercent = scorePercent,
      correctCount = correctCount,
      totalCount = totalCount,
      pointsEarned = pointsEarned,
      timeSpentSeconds = timeSpentSeconds,
      date = dateFormat.format(Date()),
      skillCategory = challenge.skillCategory,
      difficulty = challenge.difficulty
    )

    _attempts.update { listOf(attempt) + it }

    // Update Student Points, Level, and Department Points
    _currentUser.update { current ->
      if (current != null) {
        val newPoints = current.points + pointsEarned
        val (newLevel, newTitle) = calculateLevel(newPoints)
        val newCompleted = current.completedChallengesCount + 1
        val newHours = current.hoursSpent + (timeSpentSeconds / 3600f)

        // Check if unlocked new achievements
        var unlockedCount = current.unlockedAchievementsCount
        if (newCompleted >= 1) unlockAchievement("ach_1")
        if (challenge.skillCategory.contains("قواعد البيانات") && scorePercent >= 90) {
          unlockAchievement("ach_4")
          unlockedCount++
        }
        if (challenge.skillCategory.contains("الأمن السيبراني") && scorePercent == 100) {
          unlockAchievement("ach_8")
          unlockedCount++
        }

        current.copy(
          points = newPoints,
          levelNumber = newLevel,
          levelTitle = newTitle,
          completedChallengesCount = newCompleted,
          hoursSpent = (newHours * 10).toInt() / 10f,
          unlockedAchievementsCount = unlockedCount
        )
      } else null
    }

    // Update Department points
    val userDept = _currentUser.value?.department ?: "تقنية المعلومات"
    _departmentRankings.update { list ->
      list.map { dep ->
        if (dep.departmentName == userDept) {
          dep.copy(totalPoints = dep.totalPoints + pointsEarned)
        } else dep
      }.sortedByDescending { it.totalPoints }.mapIndexed { index, dep -> dep.copy(rank = index + 1) }
    }

    // Update Skill proficiency score
    _skills.update { skillsList ->
      skillsList.map { skill ->
        if (skill.nameAr.contains(challenge.skillCategory) || challenge.skillCategory.contains(skill.nameAr) || skill.nameEn.equals(challenge.skillCategory, ignoreCase = true)) {
          val newScore = ((skill.score * 0.7f) + (scorePercent * 0.3f)).toInt().coerceIn(0, 100)
          val (statAr, statEn) = calculateSkillStatus(newScore)
          skill.copy(score = newScore, levelStatusAr = statAr, levelStatusEn = statEn)
        } else skill
      }
    }

    // Add Notification
    _notifications.update { currentList ->
      listOf(
        AppNotification(
          id = "notif_${UUID.randomUUID().toString().take(6)}",
          title = "أحسنت! أكملت تحدي ${challenge.title} 🎉",
          message = "حصلت على $pointsEarned نقطة بنسبة دقة $scorePercent%. تم تحديث مستواك وترتيب القسم.",
          timeAgo = "الآن",
          type = "challenge",
          isRead = false
        )
      ) + currentList
    }

    // Recalculate AI recommendations
    refreshAIRecommendations()

    return attempt
  }

  private fun unlockAchievement(achievementId: String) {
    _achievements.update { list ->
      list.map { ach ->
        if (ach.id == achievementId && !ach.unlocked) {
          ach.copy(unlocked = true, unlockedDate = "اليوم")
        } else ach
      }
    }
  }

  private fun calculateLevel(points: Int): Pair<Int, String> {
    return when {
      points < 1500 -> 1 to "مبتدئ"
      points < 3500 -> 2 to "متعلم"
      points < 6000 -> 3 to "متقدم"
      points < 9000 -> 4 to "محترف"
      else -> 5 to "خبير"
    }
  }

  private fun calculateSkillStatus(score: Int): Pair<String, String> {
    return when {
      score >= 88 -> "ممتاز" to "Excellent"
      score >= 75 -> "جيد جدًا" to "Very Good"
      score >= 60 -> "جيد" to "Good"
      score >= 50 -> "متوسط" to "Medium"
      else -> "يحتاج إلى تطوير" to "Needs Work"
    }
  }

  fun refreshAIRecommendations() {
    val currentSkills = _skills.value
    // Find the skill with the lowest score
    val lowestSkill = currentSkills.minByOrNull { it.score } ?: currentSkills.firstOrNull()
    if (lowestSkill != null) {
      val matchingChallenge = _challenges.value.find {
        it.skillCategory.contains(lowestSkill.nameAr) || lowestSkill.nameAr.contains(it.skillCategory)
      } ?: _challenges.value.first()

      val reasonAr = when {
        lowestSkill.score < 55 -> "تشير تحليلات أدائك الأخيرة إلى أن مهارة '${lowestSkill.nameAr}' حاصلة على تقييم (${lowestSkill.score}%)، وننصحك بتركيز جهودك هنا لسد الفجوة المهارية وتفادي خسارة النقاط في المسابقات القادمة."
        lowestSkill.score < 75 -> "أنت في مسار جيد في '${lowestSkill.nameAr}' بنسبة (${lowestSkill.score}%)، ولكن حل هذا التحدي سيرفع تقييمك إلى فئة 'جيد جدًا' ويعزز ترتيبك في الكلية."
        else -> "أداؤك متميز! ننصحك بخوض هذا التحدي المتخصص لمواصلة تصدرك للقسم والوصول لمستوى 'الخبير'."
      }

      val reasonEn = "Performance diagnostics show '${lowestSkill.nameEn}' at ${lowestSkill.score}%. Practicing this recommended challenge will optimize your skill curve."

      _aiRecommendation.value = AIRecommendation(
        skillName = lowestSkill.nameAr,
        reasonAr = reasonAr,
        reasonEn = reasonEn,
        suggestedChallengeId = matchingChallenge.id,
        suggestedChallengeTitle = matchingChallenge.title,
        urgency = if (lowestSkill.score < 55) "عالية ⚡" else "تطويرية 💡"
      )
    }
  }

  fun createChallenge(
    title: String,
    description: String,
    skillCategory: String,
    major: String,
    difficulty: String,
    durationMinutes: Int,
    points: Int,
    questions: List<Question>
  ): Challenge {
    val challenge = Challenge(
      id = "ch_custom_${UUID.randomUUID().toString().take(6)}",
      title = title,
      description = description,
      skillCategory = skillCategory,
      major = major,
      difficulty = difficulty,
      durationMinutes = durationMinutes,
      points = points,
      participantsCount = 1,
      isTeam = false,
      createdBy = _currentUser.value?.name ?: "د. هيئة التدريس",
      questions = questions
    )
    _challenges.update { listOf(challenge) + it }

    _notifications.update { current ->
      listOf(
        AppNotification(
          id = "notif_${UUID.randomUUID().toString().take(6)}",
          title = "تحدي جديد منشور 📝",
          message = "تم نشر تحدي '$title' بنجاح وأصبح متاحاً لجميع الطلاب.",
          timeAgo = "الآن",
          type = "challenge",
          isRead = false
        )
      ) + current
    }

    return challenge
  }

  fun deleteChallenge(challengeId: String) {
    _challenges.update { list -> list.filter { it.id != challengeId } }
  }

  fun joinCompetition(competitionId: String) {
    _competitions.update { list ->
      list.map { comp ->
        if (comp.id == competitionId) {
          comp.copy(isJoined = !comp.isJoined, participantsCount = if (!comp.isJoined) comp.participantsCount + 1 else comp.participantsCount - 1)
        } else comp
      }
    }
  }

  fun createTeam(name: String, department: String) {
    val newTeam = Team(
      id = "team_${UUID.randomUUID().toString().take(6)}",
      name = name,
      department = department,
      avatar = "🚀",
      memberNames = listOf(_currentUser.value?.name ?: "طالب جديد"),
      totalPoints = _currentUser.value?.points ?: 0,
      competitionsWon = 0,
      rank = _teams.value.size + 1
    )
    _teams.update { listOf(newTeam) + it }
  }

  fun joinTeam(teamId: String) {
    val currentUserName = _currentUser.value?.name ?: "طالب"
    _teams.update { list ->
      list.map { team ->
        if (team.id == teamId && !team.memberNames.contains(currentUserName)) {
          team.copy(
            memberNames = team.memberNames + currentUserName,
            totalPoints = team.totalPoints + (_currentUser.value?.points ?: 0)
          )
        } else team
      }
    }
  }

  fun addCustomSkill(nameAr: String, nameEn: String, category: String) {
    val newSkill = SkillItem(
      id = "sk_${UUID.randomUUID().toString().take(6)}",
      nameAr = nameAr,
      nameEn = nameEn,
      category = category,
      score = 50,
      levelStatusAr = "متوسط",
      levelStatusEn = "Medium",
      iconName = "stars"
    )
    _skills.update { it + newSkill }
  }

  fun adminUpdateUserStatus(userId: String, newStatus: String) {
    _userAccounts.update { list ->
      list.map { user ->
        if (user.id == userId) user.copy(status = newStatus) else user
      }
    }
  }

  fun adminChangeUserRole(userId: String, newRole: UserRole) {
    _userAccounts.update { list ->
      list.map { user ->
        if (user.id == userId) user.copy(role = newRole) else user
      }
    }
  }

  fun adminAddUser(name: String, email: String, role: UserRole, department: String) {
    val newAcc = UserAccount(
      id = "user_${UUID.randomUUID().toString().take(6)}",
      name = name,
      email = email,
      role = role,
      department = department,
      status = "نشط",
      points = 100
    )
    _userAccounts.update { it + newAcc }
  }

  fun markAllNotificationsRead() {
    _notifications.update { list -> list.map { it.copy(isRead = true) } }
  }

  fun clearNotifications() {
    _notifications.value = emptyList()
  }
}
