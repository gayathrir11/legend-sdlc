// Copyright 2020 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.finos.legend.sdlc.server.inmemory.backend.api;

import org.finos.legend.sdlc.domain.model.project.configuration.ArtifactTypeGenerationConfiguration;
import org.finos.legend.sdlc.domain.model.project.configuration.ProjectConfiguration;
import org.finos.legend.sdlc.domain.model.project.configuration.ProjectStructureVersion;
import org.finos.legend.sdlc.domain.model.project.workspace.WorkspaceType;
import org.finos.legend.sdlc.domain.model.revision.Revision;
import org.finos.legend.sdlc.domain.model.version.VersionId;
import org.finos.legend.sdlc.server.domain.api.project.ProjectConfigurationApi;
import org.finos.legend.sdlc.server.domain.api.project.ProjectConfigurationUpdater;
import org.finos.legend.sdlc.server.inmemory.backend.InMemoryBackend;
import org.finos.legend.sdlc.server.inmemory.domain.api.InMemoryPatch;
import org.finos.legend.sdlc.server.inmemory.domain.api.InMemoryProject;
import org.finos.legend.sdlc.server.inmemory.domain.api.InMemoryRevision;
import org.finos.legend.sdlc.server.inmemory.domain.api.InMemoryVersion;
import org.finos.legend.sdlc.server.project.ProjectConfigurationStatusReport;

import java.util.List;
import javax.inject.Inject;

public class InMemoryProjectConfigurationApi implements ProjectConfigurationApi
{
    private final InMemoryBackend backend;

    @Inject
    public InMemoryProjectConfigurationApi(InMemoryBackend backend)
    {
        this.backend = backend;
    }

    @Override
    public ProjectConfiguration getProjectProjectConfiguration(String projectId, String patchReleaseVersion)
    {
        InMemoryProject project = this.backend.getProject(projectId);
        return project.getCurrentRevision().getConfiguration();
    }

    @Override
    public ProjectConfiguration getProjectRevisionProjectConfiguration(String projectId, String patchReleaseVersion, String revisionId)
    {
        InMemoryProject project = this.backend.getProject(projectId);
        if (patchReleaseVersion != null)
        {
            InMemoryPatch patch = project.getPatch(patchReleaseVersion);
            InMemoryRevision revision = patch.getRevision(revisionId);
            return revision.getConfiguration();
        }
        else
        {
            InMemoryRevision revision = project.getRevision(revisionId);
            return revision.getConfiguration();
        }
    }

    @Override
    public ProjectConfiguration getWorkspaceProjectConfiguration(String projectId, String patchReleaseVersion, String workspaceId, WorkspaceType workspaceType)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public ProjectConfiguration getBackupWorkspaceProjectConfiguration(String projectId, String patchReleaseVersion, String workspaceId, WorkspaceType workspaceType)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public ProjectConfiguration getWorkspaceWithConflictResolutionProjectConfiguration(String projectId, String patchReleaseVersion, String workspaceId, WorkspaceType workspaceType)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public ProjectConfiguration getWorkspaceRevisionProjectConfiguration(String projectId, String patchReleaseVersion, String workspaceId, WorkspaceType workspaceType, String revisionId)
    {
        InMemoryProject project = backend.getProject(projectId);
        InMemoryRevision revision = workspaceType == WorkspaceType.GROUP ? project.getGroupWorkspace(workspaceId, patchReleaseVersion).getRevision(revisionId) : project.getUserWorkspace(workspaceId, patchReleaseVersion).getRevision(revisionId);
        return revision.getConfiguration();
    }

    @Override
    public ProjectConfiguration getBackupUserWorkspaceRevisionProjectConfiguration(String projectId, String workspaceId, String revisionId)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public ProjectConfiguration getBackupGroupWorkspaceRevisionProjectConfiguration(String projectId, String workspaceId, String revisionId)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public ProjectConfiguration getBackupWorkspaceRevisionProjectConfiguration(String projectId, String patchReleaseVersion, String workspaceId, WorkspaceType workspaceType, String revisionId)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public ProjectConfiguration getWorkspaceWithConflictResolutionRevisionProjectConfiguration(String projectId, String patchReleaseVersion, String workspaceId, WorkspaceType workspaceType, String revisionId)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public ProjectConfiguration getVersionProjectConfiguration(String projectId, VersionId versionId)
    {
        InMemoryVersion version = backend.getProject(projectId).getVersion(versionId.toVersionIdString());
        return version.getConfiguration();
    }

    @Override
    public ProjectConfiguration getReviewFromProjectConfiguration(String projectId, String patchReleaseVersion, String reviewId)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public ProjectConfiguration getReviewToProjectConfiguration(String projectId, String patchReleaseVersion, String reviewId)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Revision updateProjectConfiguration(String projectId, String patchReleaseVersion, String workspaceId, WorkspaceType workspaceType, String message, ProjectConfigurationUpdater updater)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Revision updateProjectConfigurationForWorkspaceWithConflictResolution(String projectId, String workspaceId, String message, ProjectConfigurationUpdater updater)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<ArtifactTypeGenerationConfiguration> getProjectAvailableArtifactGenerations(String projectId, String patchReleaseVersion)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<ArtifactTypeGenerationConfiguration> getRevisionAvailableArtifactGenerations(String projectId, String revisionId)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<ArtifactTypeGenerationConfiguration> getWorkspaceRevisionAvailableArtifactGenerations(String projectId, String patchReleaseVersion, String workspaceId, WorkspaceType workspaceType, String revisionId)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<ArtifactTypeGenerationConfiguration> getWorkspaceAvailableArtifactGenerations(String projectId, String patchReleaseVersion, String workspaceId, WorkspaceType workspaceType)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<ArtifactTypeGenerationConfiguration> getVersionAvailableArtifactGenerations(String projectId, String versionId)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public ProjectStructureVersion getLatestProjectStructureVersion()
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public ProjectConfigurationStatusReport getProjectConfigurationStatus(String projectId, String patchReleaseVersion)
    {
        throw new UnsupportedOperationException("Not implemented");
    }
}
