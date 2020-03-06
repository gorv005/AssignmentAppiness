package com.appiness.assignmentappiness.interfaces

import androidx.annotation.IntDef
import com.appiness.assignmentappiness.utils.CommonInt.BLOCKED_OR_NEVER_ASKED
import com.appiness.assignmentappiness.utils.CommonInt.DENIED
import com.appiness.assignmentappiness.utils.CommonInt.GRANTED
import kotlin.annotation.Retention
@Retention(AnnotationRetention.SOURCE)
@IntDef(GRANTED, DENIED, BLOCKED_OR_NEVER_ASKED)
annotation class PermissionStatus

