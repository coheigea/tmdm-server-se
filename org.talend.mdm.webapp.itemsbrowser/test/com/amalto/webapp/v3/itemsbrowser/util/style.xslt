<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:t="http://www.talend.com/2010/MDM" version="2.0"><xsl:output method="xml" indent="yes" omit-xml-declaration="yes"/><xsl:template match="/COUNTERPARTY"><xsl:copy><xsl:copy-of select="CTP_ID"/><xsl:apply-templates select="/COUNTERPARTY[1]/CREDIT_LINE"/></xsl:copy></xsl:template><xsl:template match="/COUNTERPARTY[1]/CREDIT_LINE"><xsl:copy><xsl:apply-templates select="/COUNTERPARTY[1]/CREDIT_LINE[1]/CREDIT_LINE"/></xsl:copy></xsl:template><xsl:template match="/COUNTERPARTY[1]/CREDIT_LINE[1]/CREDIT_LINE"><xsl:copy><xsl:apply-templates select="/COUNTERPARTY[1]/CREDIT_LINE[1]/CREDIT_LINE[1]/CREDIT_LIMIT"/></xsl:copy></xsl:template><xsl:template match="/COUNTERPARTY[1]/CREDIT_LINE[1]/CREDIT_LINE[1]/CREDIT_LIMIT"><xsl:copy><xsl:copy-of select="CREDIT_LIMIT"/><xsl:copy-of select="RELATED_PURCHASE_SALE"/></xsl:copy></xsl:template><xsl:template match="/COUNTERPARTY[1]/CREDIT_LINE[1]/CREDIT_LINE"><xsl:copy><xsl:apply-templates select="/COUNTERPARTY[1]/CREDIT_LINE[1]/CREDIT_LINE[2]/CREDIT_LIMIT"/></xsl:copy></xsl:template><xsl:template match="/COUNTERPARTY[1]/CREDIT_LINE[1]/CREDIT_LINE[2]/CREDIT_LIMIT"><xsl:copy><xsl:copy-of select="CREDIT_LIMIT"/><xsl:copy-of select="RELATED_PURCHASE_SALE"/></xsl:copy></xsl:template></xsl:stylesheet>