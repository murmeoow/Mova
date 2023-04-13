package dm.sample.data.repositories

import dm.sample.data.repositories.base.BaseRepository
import dm.sample.mova.domain.entities.Faq
import dm.sample.mova.domain.entities.FaqCategory
import dm.sample.mova.domain.repositories.FaqRepository
import com.google.gson.Gson
import javax.inject.Inject

class FaqRepositoryImpl @Inject constructor(
    val gson: Gson,
) : FaqRepository, BaseRepository(gson) {

    override suspend fun fetchFaqCategories(): List<FaqCategory> = doRequest {

        val categorySet = mutableSetOf<FaqCategory>()
        FAQ_MOCK.forEach { faq ->
            faq.category.forEach {
                categorySet.add(it)
            }
        }
        categorySet.toList()
    }

    override suspend fun fetchFaq(
        keyword: String,
        categories: List<FaqCategory>,
    ): List<Faq> = doRequest {

        var result = FAQ_MOCK
        if (categories.isNotEmpty()) {
            result = result.filter { faq ->
                faq.category.forEach {
                    if (categories.contains(it)) return@filter true
                }
                false
            }
        }
        if (keyword.isNotBlank()) {
            result = result.filter {
                it.question.contains(keyword, ignoreCase = true)
            }
        }
        result
    }


    companion object {
        val FAQ_MOCK = listOf(
            Faq(
                question = "What is MOVA?",
                answer = "With MOVA apps, you can watch series and movies on Android and iOS. largest database of series and movies, whose users are always up to date with key industry events. The service application allows you to watch trailers. Users can view lists of actors involved in each project and brief descriptions of plots. With the help of the service, movies and series can be downloaded to your device or watched online. The user almost always has a choice of video quality and subtitles. Many films contain multiple audio tracks in different languages.",
                category = listOf(
                    FaqCategory(id = 1, name = "General")
                )
            ),
            Faq(
                question = "Can I change/set/reset my password or email?",
                answer = "Yes, you can. Click on your name in the top line, and select \"Personal Data\" in the menu that opens. All this can be configured there",
                category = listOf(
                    FaqCategory(id = 2, name = "Account")
                )
            ),
            Faq(
                question = "What can you watch for free?",
                answer = "Part is available without subscription, series, movies and shows. If a subscription film appears, you will see an inscription by subscription",
                category = listOf(
                    FaqCategory(id = 1, name = "General")
                )
            ),
            Faq(
                question = "I forgot my password. What should I do?",
                answer = "If you entered an email – use the \"Remind password\" function in the login form",
                category = listOf(
                    FaqCategory(id = 2, name = "Account")
                )
            ),
            Faq(
                question = "How do I find a movie?",
                answer = "Use the search tap on Explore– at the bottom.The search section opens, you need to click on the magnifying glass in the top row. If you know about the year of the film",
                category = listOf(
                    FaqCategory(id = 3, name = "Video")
                )
            ),
            Faq(
                question = "How can I download movies on my phone?",
                answer = "1.Make sure your device is connected to the internet.\n2.Find the movie or TV episode you want to download. \n3.Open movie details \n4.Tap on bottom Download \n5.Wait for the movie to download",
                category = listOf(
                    FaqCategory(id = 3, name = "Video")
                )
            ),
            Faq(
                question = "How to add on My list movies?",
                answer = "MOVA service users have the ability to add movies and series to My list to watch them later by clicking on the heart in the movie details",
                category = listOf(
                    FaqCategory(id = 3, name = "Video")
                )
            ),
            Faq(
                question = "Why do you need a subscription?",
                answer = "New series and movies are released every week, some of which are available only on the MOVA service. If you subscribe, then do not miss the new episodes of your favorite series. The subscription is issued for 30 days or a year and is renewed automatically",
                category = listOf(
                    FaqCategory(id = 1, name = "General")
                )
            ),
            Faq(
                question = "What is included in the subscription?",
                answer = "The subscription includes all movies, series, shows and TV channels available on the MOVA service",
                category = listOf(
                    FaqCategory(id = 1, name = "General")
                )
            ),
            Faq(
                question = "How to activate a subscription?",
                answer = "Open Profile\ntap to subscribe.\n\n1.Select a subscription \n2.Select a payment method or add a card \n3.In the form that opens, enter the card number, expiration date and CVC/CVV code. \n4. Confirm payment by card by entering the code from SMS into the form.\n5.Done. You can watch your favorite movies and series!",
                category = listOf(
                    FaqCategory(id = 1, name = "General")
                )
            ),
            Faq(
                question = "Problem launching app or video?",
                answer = "Check your TV's internet connection.\nUpdate your TV firmware to the latest version. Delete the app from your device memory and download it again from the app store. Log in again to the account on the device where you enter the code that is displayed on the TV. Re-enter your username (phone number) and password.Unplug the TV from power for a few minutes, then reinstall the app.Problem launching app or video",
                category = listOf(
                    FaqCategory(id = 1, name = "General"),
                    FaqCategory(id = 3, name = "Video")
                )
            ),
            Faq(
                question = "When will my card be charged for the subscription?",
                answer = "The subscription renews automatically for the next calendar month after the subscription you purchased expires. Money will be debited 1 day before the end of the subscription. See the profile for the date until which the current subscription is valid",
                category = listOf(
                    FaqCategory(id = 2, name = "Account")
                )
            ),
            Faq(
                question = "What cards can be used to pay for a subscription?",
                answer = "The subscription can be paid on the territory of the Russian Federation using Russian banking MIR, Visa, Mastercard, Maestro. Transactions are encrypted and completely secure.",
                category = listOf(
                    FaqCategory(id = 2, name = "Account")
                )
            ),
            Faq(
                question = "Why you need to sign in?",
                answer = "Only registered and authorized users can subscribe and watch shows, series, movies",
                category = listOf(
                    FaqCategory(id = 2, name = "Account")
                )
            ),
            Faq(
                question = "How to cancel a subscription?",
                answer = "Go to my profile. Tap subscribe, tap unsubscribe",
                category = listOf(
                    FaqCategory(id = 1, name = "General")
                )
            )
        )
    }
}