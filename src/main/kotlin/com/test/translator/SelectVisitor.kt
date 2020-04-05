package com.test.translator

import com.test.parser.SelectBaseVisitor
import com.test.parser.SelectParser

internal class SelectVisitor : SelectBaseVisitor<Unit>() {
    lateinit var collector : SelectOperationCollector

    override fun visitSelect(ctx: SelectParser.SelectContext) {
        collector = SelectOperationCollector(ctx.collection().NAME().text)
        super.visitSelect(ctx)
    }

    override fun visitColumnName(ctx: SelectParser.ColumnNameContext) {
        val name = listOfNotNull(ctx.FIELD_NAME(), ctx.NAME()).single().text
        collector.columns.add(name)
    }

    override fun visitEqCondition(ctx: SelectParser.EqConditionContext) {
        collector.where.add(EqCondition(ctx.left.text, ctx.right.text))
    }

    override fun visitGtCondition(ctx: SelectParser.GtConditionContext) {
        collector.where.add(GtCondition(ctx.left.text, ctx.right.text))
    }

    override fun visitLtCondition(ctx: SelectParser.LtConditionContext) {
        collector.where.add(LtCondition(ctx.left.text, ctx.right.text))
    }

    override fun visitNeCondition(ctx: SelectParser.NeConditionContext) {
       collector. where.add(NeCondition(ctx.left.text, ctx.right.text))
    }

    override fun visitLimit(ctx: SelectParser.LimitContext) {
        collector.ranges.add("limit(${ctx.NUMBER().text})")
    }

    override fun visitSkip(ctx: SelectParser.SkipContext) {
        collector.ranges.add("skip(${ctx.NUMBER().text})")
    }
}