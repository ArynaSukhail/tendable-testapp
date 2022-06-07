package com.release.data

import com.release.data.model.*
import com.release.domain.model.AnswerItem
import com.release.domain.model.InspectionItem
import com.release.domain.model.QuestionItem

fun getFakeSigninRequest(email: String, password: String): SignInRequest {
    return SignInRequest(
        email,
        password
    )
}

fun getFakeSignURequest(email: String, password: String): SignUpRequest {
    return SignUpRequest(
        email,
        password
    )
}

fun getFakeStartResponse(): StartResponse {
    return StartResponse(
        Inspection(
            Area(
                1,
                "name"
            ),
            1,
            InspectionType(
                "access",
                1,
                "name"
            ),
            Survey(
                1,
                listOf(
                    Categories(
                        1,
                        "name",
                        arrayListOf(
                            Questions(
                                1,
                                "name",
                                1,
                                listOf(
                                    AnswerChoices(
                                        1,
                                        "name",
                                        1.1
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
    )
}

fun getFakeListOfInspectionItems(): List<InspectionItem> {
    return listOf(
        InspectionItem(
            1,
            "type",
            "area"
        )
    )
}

fun getFakeQuestionItems(): List<QuestionItem> {
    return listOf(
        QuestionItem(
            1,
            "type",
            listOf(
                AnswerItem(
                    1,
                    "name"
                )
            ),
            "category",
            1
        )
    )
}

fun getFakeSendRequest(inspection: Inspection): SendRequest {
    return SendRequest(
        inspection
    )
}

fun getFakeInspection(): Inspection {
    return Inspection(
        Area(
            1,
            "name"
        ),
        1,
        InspectionType(
            "access",
            1,
            "name"
        ),
        Survey(
            1,
            listOf(
                Categories(
                    1,
                    "name",
                    arrayListOf(
                        Questions(
                            1,
                            "name",
                            1,
                            listOf(
                                AnswerChoices(
                                    1,
                                    "name",
                                    1.1
                                )
                            )
                        )
                    )
                )
            )
        )
    )
}