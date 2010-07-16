/*
 * Ext JS Library 2.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

Ext.data.Record=function(A,B){this.id=(B||B===0)?B:++Ext.data.Record.AUTO_ID;this.data=A};Ext.data.Record.create=function(E){var C=Ext.extend(Ext.data.Record,{});var D=C.prototype;D.fields=new Ext.util.MixedCollection(false,function(F){return F.name});for(var B=0,A=E.length;B<A;B++){D.fields.add(new Ext.data.Field(E[B]))}C.getField=function(F){return D.fields.get(F)};return C};Ext.data.Record.AUTO_ID=1000;Ext.data.Record.EDIT="edit";Ext.data.Record.REJECT="reject";Ext.data.Record.COMMIT="commit";Ext.data.Record.prototype={dirty:false,editing:false,error:null,modified:null,join:function(A){this.store=A},set:function(A,B){if(String(this.data[A])==String(B)){return }this.dirty=true;if(!this.modified){this.modified={}}if(typeof this.modified[A]=="undefined"){this.modified[A]=this.data[A]}this.data[A]=B;if(!this.editing&&this.store){this.store.afterEdit(this)}},get:function(A){return this.data[A]},beginEdit:function(){this.editing=true;this.modified={}},cancelEdit:function(){this.editing=false;delete this.modified},endEdit:function(){this.editing=false;if(this.dirty&&this.store){this.store.afterEdit(this)}},reject:function(B){var A=this.modified;for(var C in A){if(typeof A[C]!="function"){this.data[C]=A[C]}}this.dirty=false;delete this.modified;this.editing=false;if(this.store&&B!==true){this.store.afterReject(this)}},commit:function(A){this.dirty=false;delete this.modified;this.editing=false;if(this.store&&A!==true){this.store.afterCommit(this)}},getChanges:function(){var A=this.modified,B={};for(var C in A){if(A.hasOwnProperty(C)){B[C]=this.data[C]}}return B},hasError:function(){return this.error!=null},clearError:function(){this.error=null},copy:function(A){return new this.constructor(Ext.apply({},this.data),A||this.id)},isModified:function(A){return !!(this.modified&&this.modified.hasOwnProperty(A))}};