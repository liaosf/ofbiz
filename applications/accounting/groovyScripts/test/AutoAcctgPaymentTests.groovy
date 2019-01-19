/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/


import org.apache.ofbiz.entity.GenericValue
import org.apache.ofbiz.entity.util.EntityQuery
import org.apache.ofbiz.service.ServiceUtil
import org.apache.ofbiz.testtools.GroovyScriptTestCase

class AutoAcctgPaymentTests extends GroovyScriptTestCase {
    void testCreatePayment() {
        Map serviceCtx = [:]
        serviceCtx.paymentTypeId = 'CUSTOMER_PAYMENT'
        serviceCtx.partyIdFrom = 'Company'
        serviceCtx.partyIdTo = 'DemoCustCompany'
        serviceCtx.amount = new BigDecimal('100.00')
        serviceCtx.paymentMethodTypeId = 'COMPANY_CHECK'
        serviceCtx.userLogin = EntityQuery.use(delegator).from('UserLogin').where('userLoginId', 'system').cache().queryOne()
        Map serviceResult = dispatcher.runSync('createPayment', serviceCtx)
        assert ServiceUtil.isSuccess(serviceResult)

        GenericValue payment = EntityQuery.use(delegator).from('Payment').where('paymentId', serviceResult.paymentId).queryOne()
        assert payment.paymentTypeId == 'CUSTOMER_PAYMENT'
        assert payment.paymentMethodTypeId == 'COMPANY_CHECK'
    }
}
