/*
 * The MIT License
 *
 * Copyright (c) 2020, CloudBees, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.cloudbees.jenkins.plugins.bitbucket;

import hudson.Extension;
import jenkins.scm.api.SCMSource;
import jenkins.scm.api.trait.SCMSourceContext;
import jenkins.scm.api.trait.SCMSourceTrait;
import jenkins.scm.api.trait.SCMSourceTraitDescriptor;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

/**
 * A {@link SCMSourceTrait} for {@link BitbucketSCMSource} that sets how notifications
 * are sent to Bitbucket.
 *
 * @since 2.10.0
 */
public class BitbucketBuildStatusNotificationsTrait extends SCMSourceTrait {

    /**
     * Should unstable builds be communicated as success to Bitbucket
     */
    private boolean sendSuccessNotificationForUnstableBuild;


    private boolean sendNotificationsForPrBuildsOnly;

    /**
     * Constructor.
     *
     */
    @DataBoundConstructor
    public BitbucketBuildStatusNotificationsTrait() {
        /**
         * empty constructor
         */
    }

    @DataBoundSetter
    public void setSendSuccessNotificationForUnstableBuild(boolean isSendSuccess) {
        sendSuccessNotificationForUnstableBuild = isSendSuccess;
    }

    @DataBoundSetter
    public void setSendNotificationsForPrBuildsOnly(boolean isSendPrOnly) {
        sendNotificationsForPrBuildsOnly = isSendPrOnly;
    }

    /**
     * @return if unstable builds will be communicated as successful
     */
    public boolean getSendSuccessNotificationForUnstableBuild() {
        return this.sendSuccessNotificationForUnstableBuild;
    }

    public boolean getSendNotificationsForPrBuildsOnly() {
        return this.sendNotificationsForPrBuildsOnly;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decorateContext(SCMSourceContext<?, ?> context) {
        ((BitbucketSCMSourceContext) context).withSendSuccessNotificationForUnstableBuild(getSendSuccessNotificationForUnstableBuild());
        ((BitbucketSCMSourceContext) context).withSendNotificationsForPrBuildsOnly(getSendNotificationsForPrBuildsOnly());
    }

    /**
     * Our constructor.
     */
    @Symbol("bitbucketBuildStatusNotifications")
    @Extension
    public static class DescriptorImpl extends SCMSourceTraitDescriptor {

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDisplayName() {
            return Messages.BitbucketBuildStatusNotificationsTrait_displayName();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Class<? extends SCMSourceContext> getContextClass() {
            return BitbucketSCMSourceContext.class;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Class<? extends SCMSource> getSourceClass() {
            return BitbucketSCMSource.class;
        }
    }
}
