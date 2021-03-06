package org.storymaker.app.publish.sites;

import timber.log.Timber;

import android.content.Context;
import android.util.Log;
import org.storymaker.app.model.Auth;
import org.storymaker.app.model.Job;
import org.storymaker.app.model.JobTable;
import org.storymaker.app.model.PublishJob;
import org.storymaker.app.publish.PublishController;
import org.storymaker.app.publish.PublisherBase;

public class SoundCloudPublisher extends PublisherBase {
    private final String TAG = "SoundCloudPublisher";

    public SoundCloudPublisher(Context context, PublishController publishController, PublishJob publishJob) {
        super(context, publishController, publishJob);
    }
    
    public void startRender() {
        Timber.d("startRender");
        // TODO should detect if user is directly publishing to youtube so we don't double publish to there
        
        Job job = new Job(mContext, mPublishJob.getProjectId(), mPublishJob.getId(), JobTable.TYPE_RENDER, null, AudioRenderer.SPEC_KEY);
        mController.enqueueJob(job);
    }
    
    public void startUpload() {
        Timber.d("startUpload");
        Job newJob = new Job(mContext, mPublishJob.getProjectId(), mPublishJob.getId(), JobTable.TYPE_UPLOAD, Auth.SITE_SOUNDCLOUD, null);
        mController.enqueueJob(newJob);
    }

    @Override
    public String getEmbed(Job job) {

        String embed = "[soundcloud url=\\\"https://api.soundcloud.com/tracks/"
                + job.getResult()
                + "\\\" params=\\\"color=00cc11&auto_play=false&hide_related=false&show_artwork=true\\\" width=\\\"100%\\\" height=\\\"166\\\" iframe=\\\"true\\\" /]";

        // without escape characters:
        // [soundcloud url="https://api.soundcloud.com/tracks/<number>" params="color=00cc11&auto_play=false&hide_related=false&show_artwork=true" width="100%" height="166" iframe="true" /]

        return embed;

        //return "[soundcloud url=\"https://api.soundcloud.com/tracks/"
        //        + job.getResult() + "\" params=\"color=00cc11&auto_play=false&hide_related=false&show_artwork=true\" width=\"100%\" height=\"166\" iframe=\"true\" /]";

        // [soundcloud url="https://api.soundcloud.com/tracks/156197566" params="auto_play=false&hide_related=false&show_comments=true&show_user=true&show_reposts=false&visual=true" width="100%" height="450" iframe="true" /]
    }

    @Override
    public String getResultUrl(Job job) {

        // permalink_url ?
        return null; // FIXME implement getResultUrl
    }
}
