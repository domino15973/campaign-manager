import React, {useState} from 'react';
import {Campaign, createCampaign, updateCampaign} from '../services/campaignService';
import AsyncSelect from 'react-select/async';
import {getTowns} from '../services/townService';
import {getKeywords} from '../services/keywordService';

interface CampaignFormProps {
    campaign: Campaign | null;
    onClose: () => void;
}

const CampaignForm: React.FC<CampaignFormProps> = ({campaign, onClose}) => {
    const [formData, setFormData] = useState({
        name: campaign?.name || '',
        keywords: campaign?.keywords || [],
        bidAmount: campaign?.bidAmount || 0,
        campaignFund: campaign?.campaignFund || 0,
        status: campaign?.status || false,
        town: campaign?.town || '',
        radius: campaign?.radius || 0,
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        // @ts-ignore
        const {name, value, type, checked} = e.target;

        if (type === 'checkbox') {
            setFormData(prevData => ({
                ...prevData,
                [name]: checked,
            }));
        } else if (name === 'status') {
            setFormData(prevData => ({
                ...prevData,
                status: value === 'true',
            }));
        } else {
            setFormData(prevData => ({
                ...prevData,
                [name]: value,
            }));
        }
    };

    const handleKeywordsChange = (selectedOptions: any) => {
        setFormData(prevData => ({
            ...prevData,
            keywords: selectedOptions ? selectedOptions.map((option: any) => option.value) : [],
        }));
    };

    const handleTownChange = (selectedOption: any) => {
        setFormData(prevData => ({
            ...prevData,
            town: selectedOption ? selectedOption.value : ''
        }));
    };

    const loadOptions = async (inputValue: string) => {
        const keywords = await getKeywords(inputValue);
        return keywords.map((keyword: string) => ({value: keyword, label: keyword}));
    };

    const loadTownOptions = async (inputValue: string) => {
        const towns = await getTowns(inputValue);
        return towns.map((town: string) => ({value: town, label: town}));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (formData.keywords.length === 0) {
            alert("Please select at least one keyword.");
            return;
        }

        if (!formData.town) {
            alert("Please select a town.");
            return;
        }

        if (campaign) {
            if (campaign.id != null) {
                await updateCampaign(campaign.id, formData);
            }
        } else {
            await createCampaign(formData);
        }
        onClose();
    };


    return (
        <form onSubmit={handleSubmit}>
            <label>
                Campaign Name:
                <input
                    type="text"
                    name="name"
                    value={formData.name}
                    onChange={handleChange}
                    required
                />
            </label>
            <label>
                Keywords:
                <AsyncSelect
                    isMulti
                    cacheOptions
                    defaultOptions
                    loadOptions={loadOptions}
                    onChange={handleKeywordsChange}
                    value={formData.keywords.map(keyword => ({value: keyword, label: keyword}))}
                />
            </label>
            <label>
                Bid Amount:
                <input
                    type="number"
                    name="bidAmount"
                    value={formData.bidAmount}
                    onChange={handleChange}
                    required
                    min="1"
                />
            </label>
            <label>
                Campaign Fund:
                <input
                    type="number"
                    name="campaignFund"
                    value={formData.campaignFund}
                    onChange={handleChange}
                    required
                    min="1"
                />
            </label>
            <label>
                Status:
                <select name="status" value={formData.status ? 'true' : 'false'} onChange={handleChange} required>
                    <option value="true">On</option>
                    <option value="false">Off</option>
                </select>
            </label>
            <label>
                Town:
                <AsyncSelect
                    cacheOptions
                    loadOptions={loadTownOptions}
                    onChange={handleTownChange}
                    value={formData.town ? {value: formData.town, label: formData.town} : null}
                    isClearable
                />
            </label>
            <label>
                Radius (km):
                <input
                    type="number"
                    name="radius"
                    value={formData.radius}
                    onChange={handleChange}
                    required
                    min="1"
                />
            </label>
            <button type="submit">Save</button>
            <button type="button" onClick={onClose}>Cancel</button>
        </form>
    );
};

export default CampaignForm;
